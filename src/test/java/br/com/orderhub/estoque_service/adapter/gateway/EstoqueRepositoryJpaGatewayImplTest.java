package br.com.orderhub.estoque_service.adapter.gateway;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.estoque_service.adapter.mapper.EstoqueEntityMapper;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários do EstoqueRepositoryJpaGatewayImpl")
class EstoqueRepositoryJpaGatewayImplTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @Mock
    private EstoqueEntityMapper mapper;

    @InjectMocks
    private EstoqueRepositoryJpaGatewayImpl estoqueGateway;

    private Estoque estoqueDominio;
    private EstoqueEntity estoqueEntidade;

    @BeforeEach
    void setUp() {
        estoqueDominio = Estoque.builder().id(1L).quantidadeDisponivel(100).build();
        estoqueEntidade = EstoqueEntity.builder().id(1L).quantidadeDisponivel(100).build();
    }

    @Test
    @DisplayName("Deve buscar por ID e encontrar um estoque")
    void deveBuscarPorIdComSucesso() {
        // Arrange
        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoqueEntidade));
        when(mapper.toDomain(estoqueEntidade)).thenReturn(estoqueDominio);

        // Act
        Optional<Estoque> resultado = estoqueGateway.buscarPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(estoqueDominio.getId(), resultado.get().getId());
        verify(estoqueRepository).findById(1L);
        verify(mapper).toDomain(estoqueEntidade);
    }

    @Test
    @DisplayName("Deve buscar por ID e não encontrar um estoque")
    void deveRetornarVazioAoBuscarPorIdInexistente() {
        // Arrange
        when(estoqueRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Estoque> resultado = estoqueGateway.buscarPorId(99L);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(estoqueRepository).findById(99L);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    @DisplayName("Deve buscar por múltiplos IDs com sucesso")
    void deveBuscarPorIdsComSucesso() {
        // Arrange
        when(estoqueRepository.findAllById(List.of(1L))).thenReturn(List.of(estoqueEntidade));
        when(mapper.toDomain(estoqueEntidade)).thenReturn(estoqueDominio);

        // Act
        List<Estoque> resultado = estoqueGateway.buscarPorIds(List.of(1L));

        // Assert
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(estoqueDominio.getId(), resultado.get(0).getId());
    }
    
    @Test
    @DisplayName("Deve retornar lista vazia ao buscar por múltiplos IDs inexistentes")
    void deveRetornarListaVaziaAoBuscarPorIdsInexistentes() {
        // Arrange
        when(estoqueRepository.findAllById(anyList())).thenReturn(Collections.emptyList());

        // Act
        List<Estoque> resultado = estoqueGateway.buscarPorIds(List.of(99L, 100L));

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar lista vazia ao buscar por uma lista de IDs vazia")
    void deveRetornarListaVaziaAoBuscarPorListaDeIdsVazia() {
        // Arrange
        when(estoqueRepository.findAllById(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act
        List<Estoque> resultado = estoqueGateway.buscarPorIds(Collections.emptyList());

        // Assert
        assertTrue(resultado.isEmpty());
        verify(estoqueRepository).findAllById(Collections.emptyList());
        verify(mapper, never()).toDomain(any());
    }

    @Test
    @DisplayName("Deve salvar um estoque com sucesso")
    void deveSalvarComSucesso() {
        // Arrange
        when(mapper.toEntity(estoqueDominio)).thenReturn(estoqueEntidade);

        // Act
        estoqueGateway.salvar(estoqueDominio);

        // Assert
        verify(mapper).toEntity(estoqueDominio);
        verify(estoqueRepository).save(estoqueEntidade);
    }
}