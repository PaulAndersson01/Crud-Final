package pe.edu.upeu.padronadultos.config;

import pe.edu.upeu.padronadultos.controller.AdultoMayorController;
import pe.edu.upeu.padronadultos.controller.MainguiController;
import pe.edu.upeu.padronadultos.repository.AdultoMayorRepository;
import pe.edu.upeu.padronadultos.service.IAdultoMayorService;
import pe.edu.upeu.padronadultos.service.impl.AdultoMayorServiceImp;

import java.util.HashMap;
import java.util.Map;


public class AppContext {


    private static AppContext instance;

    public static synchronized AppContext getInstance() {
        if (instance == null) instance = new AppContext();
        return instance;
    }


    private final Map<Class<?>, Object> contenedor = new HashMap<>();


    private AppContext() {
        registrarRepositorios();
        registrarServicios();
        registrarControladores();
    }


    private void registrarRepositorios() {
        AdultoMayorRepository adultoMayorRepository = new AdultoMayorRepository();
        adultoMayorRepository.seedData(); // datos de ejemplo
        registrar(AdultoMayorRepository.class, adultoMayorRepository);
    }


    private void registrarServicios() {
        registrar(IAdultoMayorService.class,
                new AdultoMayorServiceImp(getBean(AdultoMayorRepository.class)));
    }


    private void registrarControladores() {
        registrar(AdultoMayorController.class,
                new AdultoMayorController(getBean(IAdultoMayorService.class)));
        registrar(MainguiController.class, new MainguiController());
    }


    private void registrar(Class<?> tipo, Object bean) {
        contenedor.put(tipo, bean);
    }


    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> tipo) {
        Object bean = contenedor.get(tipo);
        if (bean == null) {
            bean = contenedor.values().stream()
                    .filter(b -> tipo.isAssignableFrom(b.getClass()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(
                            "Bean no encontrado: " + tipo.getName() +
                                    "\n→ ¿Lo registraste en AppContext?"));
        }
        return (T) bean;
    }
}
