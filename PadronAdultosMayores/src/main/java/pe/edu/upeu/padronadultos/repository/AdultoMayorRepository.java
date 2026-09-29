package pe.edu.upeu.padronadultos.repository;

import pe.edu.upeu.padronadultos.enums.EstadoSalud;
import pe.edu.upeu.padronadultos.enums.TipoPension;
import pe.edu.upeu.padronadultos.model.AdultoMayor;

public class AdultoMayorRepository extends AbstractJpaRepository<AdultoMayor, Long> {

    private long sequence = 1;

    @Override
    protected Long getId(AdultoMayor entity) {
        return entity.getId();
    }

    @Override
    protected void setId(AdultoMayor entity, Long id) {
        entity.setId(id);
    }

    @Override
    protected Long generateId() {
        return sequence++;
    }


    public void seedData() {
        if (findAll().isEmpty()) {
            save(new AdultoMayor(null, "Rosa María Quispe Mamani", 74, "QUMR520314MPNSMS08",
                    "Jr. Los Pinos 123", TipoPension.JUBILACION, EstadoSalud.BUENO));
            save(new AdultoMayor(null, "José Luis Gómez Pérez", 78, "GOLJ480720HDFMPN05",
                    "Av. Las Flores 456", TipoPension.INVALIDEZ, EstadoSalud.DELICADO));
        }
    }
}
