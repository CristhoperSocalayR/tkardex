package edu.pe.vallegrande.MovementKardex.service;

import edu.pe.vallegrande.MovementKardex.model.MovementKardex;
import edu.pe.vallegrande.MovementKardex.repository.MovementKardexRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class MovementKardexServiceTest {

    @Mock
    private MovementKardexRepository repository;

    @InjectMocks
    private MovementKardexService service;

    private MovementKardex movement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        movement = new MovementKardex();
        movement.setKardexId(1L);
        movement.setConcept("Test Concept");
    }

    @Test
    public void testListarTodos() {
        when(repository.findAllByOrderByKardexIdAsc()).thenReturn(Flux.just(movement));

        StepVerifier.create(service.listarTodos())
                .expectNext(movement)
                .verifyComplete();

        verify(repository).findAllByOrderByKardexIdAsc();
    }

    @Test
    public void testCrear() {
        when(repository.save(movement)).thenReturn(Mono.just(movement));

        StepVerifier.create(service.crear(movement))
                .expectNext(movement)
                .verifyComplete();

        verify(repository).save(movement);
    }

    @Test
    public void testEditar() {
        MovementKardex updated = new MovementKardex();
        updated.setConcept("Updated Concept");

        when(repository.findById(1L)).thenReturn(Mono.just(movement));
        when(repository.save(any(MovementKardex.class))).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(service.editar(1L, updated))
                .assertNext(result -> {
                    assert result.getConcept().equals("Updated Concept");
                })
                .verifyComplete();

        verify(repository).findById(1L);
        verify(repository).save(any(MovementKardex.class));
    }

    @Test
    public void testEliminar() {
        when(repository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(service.eliminar(1L))
                .verifyComplete();

        verify(repository).deleteById(1L);
    }
}
