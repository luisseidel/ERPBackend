package com.seidelsoft.ERPBackend;

import com.seidelsoft.ERPBackend.menu.repository.MenuRepository;
import com.seidelsoft.ERPBackend.menu.service.MenuService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Spy // 1. Usamos @Spy para criar um "espião" do serviço
    @InjectMocks
    private MenuService menuService;

    @Test
    void shouldReturnMenuName_whenMenyExists() {
        // Arrange (Arranjar)

        // 2. Forçamos o método a retornar nosso mock, resolvendo o NullPointerException
        doReturn(menuRepository).when(menuService).getSpecificRepository();

        // 3. Configuramos o comportamento do mock para o método que será chamado
        // (Assumindo que findNameById é um método customizado no seu repositório que retorna uma String)
        when(menuRepository.findNameById(1L)).thenReturn("Mock Menu");

        // Act (Agir): Executa a lógica a ser testada
        String menuName = menuService.getSpecificRepository().findNameById(1L);

        // Assert (Verificar): Valida o resultado
        assertEquals("Mock Menu", menuName);
    }

}
