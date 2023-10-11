import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.uv.pojo.Empleado;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import org.uv.hello.HibernateUtil;


@Named(value = "beanIndex")
@SessionScoped
public class BeanIndex implements Serializable {

    private int clave;
    private String nombre;
    private String direccion;
    private String telefono;
    private List<Empleado> empleados;

    public BeanIndex() {
        cargarEmpleados();
    }

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void cargarEmpleados() {
        Session session = HibernateUtil.getSession();
        try {
            empleados = session.createQuery("FROM Empleado").list();
        } finally {
            session.close();
        }
    }

    public void crearEmpleado() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Empleado empleado = new Empleado();
            empleado.setClave(clave);
            empleado.setNombre(nombre);
            empleado.setDireccion(direccion);
            empleado.setTelefono(telefono);
            session.save(empleado);
            tx.commit();
            cargarEmpleados();
            limpiarCampos();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void actualizarEmpleado(Empleado empleado) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(empleado);
            tx.commit();
            cargarEmpleados();
            limpiarCampos();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void eliminarEmpleado(Empleado empleado) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(empleado);
            tx.commit();
            cargarEmpleados();
            limpiarCampos();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private void limpiarCampos() {
        clave = 0;
        nombre = "";
        direccion = "";
        telefono = "";
    }
}
