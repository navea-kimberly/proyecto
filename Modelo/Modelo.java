package Modelo;

import ConexionMySQL.Conexion;
import Entidades.Beneficio;
import Entidades.Cliente;
import Entidades.Contrato;
import Entidades.DatosBitacora;
import Entidades.Incidencia;
import Entidades.Rol_de_Guardia;
import Entidades.Transacciones;
import Entidades.Usuario;
import Entidades.Vigilante;
import Otros.Mensajes;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Modelo {
    Conexion conectar = new Conexion();
    Connection canal;
    
    static public String usuario;
    
    // INICIAR SESIÓN
    public boolean buscarVigilante(String cod_usuario, String nombre_usuario, String clave) {
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        boolean datos = false, usuario = false;
        
        // Comprueba si tiene datos registrados en la tabla Vigilante
        String comprobarUsuario = "SELECT * FROM vigilante WHERE dni='" + cod_usuario + "'";

        try {
            st = canal.createStatement();
            rs = st.executeQuery(comprobarUsuario);

            if (rs.next()) {
                datos = true;
            }

        } catch (SQLException ex) {
            System.out.println("Error al buscar datos del Vigilante: " + ex);
        }
        
        // Comprueba si el usuario existe
        String buscar = "SELECT * FROM usuario WHERE cod_usuario='" + cod_usuario + "' AND nombre='" + nombre_usuario + "' AND clave='" + clave + "'";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar);

            if (rs.next()) {
                usuario = true;
            }

            st.close();
            rs.close();
            conectar.Desconectar();

        } catch (SQLException ex) {
            System.out.println("Error al buscar usuario del Vigilante: " + ex);
        }
        
        return (datos && usuario);
    }
    
    public int buscarUsuario(String cod_usuario, String nombre_usuario, String clave) {
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        int tipo = 0;
        
        String buscar = "SELECT * FROM usuario WHERE cod_usuario='" + cod_usuario + "' AND nombre='" + nombre_usuario + "' AND clave='" + clave + "'";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar);

            if (rs.next()) {
                tipo = rs.getInt(4);
            }

            st.close();
            rs.close();
            conectar.Desconectar();

        } catch (SQLException ex) {
            System.out.println("Error al buscar Usuario: " + ex);
        }
        
        return tipo;
    }
    
    // MÓDULO USUARIOS
    public ObservableList<Usuario> tablaUsuarios() {
        ArrayList<Usuario> datos = new ArrayList<>();
        ObservableList<Usuario> tabla;
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String mostrarUsuarios = "SELECT U.cod_usuario, U.nombre, T.nombre_tipo FROM usuario U JOIN tipo_usuario T WHERE U.id_tipo = T.idTipo_usuario";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(mostrarUsuarios);
            
            while (rs.next()) {
                Usuario _usuario = new Usuario();
                _usuario.setCod_usuario(rs.getString(1));
                _usuario.setNombre(rs.getString(2));
                _usuario.setTipo(rs.getString(3));
                
                datos.add(_usuario);
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al mostrar los Usuarios en la Tabla: " + ex);
        }
        
        tabla = FXCollections.observableList(datos);
        return tabla;
    }
    
    public int usuariosRegistrados() {
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        int cantidad = 0;
        
        String contandoUsuarios = "SELECT cod_usuario FROM usuario";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(contandoUsuarios);
            
            while (rs.next()) {
                cantidad++;
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al contar los Usuarios registrados: " + ex);
        }
        
        return cantidad;
    }
    
    public ObservableList<Usuario> consultarUsuario(String buscar) {
        ArrayList<Usuario> datos = new ArrayList<>();
        ObservableList<Usuario> tabla;
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String mostrarUsuarios = "SELECT U.cod_usuario, U.nombre, T.nombre_tipo FROM usuario U JOIN tipo_usuario T WHERE U.id_tipo = T.idTipo_usuario AND U.cod_usuario ='" + buscar + "'";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(mostrarUsuarios);
            
            if (rs.next()) {
                Usuario _usuario = new Usuario();
                _usuario.setCod_usuario(rs.getString(1));
                _usuario.setNombre(rs.getString(2));
                _usuario.setTipo(rs.getString(3));
                
                datos.add(_usuario);
                rs.close();
            }
            
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al mostrar datos del Usuario en la Tabla: " + ex);
        }
        
        tabla = FXCollections.observableList(datos);
        return tabla;
    }
    
    public int buscarTipoUsuario(char letra) {
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        int idTipo = 0;
        String buscar = "SELECT idTipo_usuario FROM tipo_usuario WHERE nombre_tipo LIKE '" + letra + "%'";

        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar);
            
            if (rs.next()) {
                idTipo = rs.getInt(1);
                
                rs.close();
            }
            
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al buscar Tipo de Usuario: " + ex);
        }
        
        return idTipo;
    }
    
    public boolean registrarUsuario(String cod_usuario, String nombre, String clave, int id_tipo) {
        Usuario _usuario = new Usuario(cod_usuario, nombre, clave, id_tipo);
        
        canal = conectar.Conectar();
        
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        boolean datos_repetidos = false;
        
        String buscar_duplicado = "SELECT cod_usuario FROM usuario WHERE cod_usuario = '" + cod_usuario + "'";
        String insertar = "INSERT INTO usuario (cod_usuario, nombre, clave, id_tipo) VALUES (?, ?, ?, ?)";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar_duplicado);
            
            if (rs.next()) {
                datos_repetidos = true;
            } else {
                ps = canal.prepareStatement(insertar);
                ps.setString(1, _usuario.getCod_usuario());
                ps.setString(2, _usuario.getNombre());
                ps.setString(3, _usuario.getClave());
                ps.setInt(4, _usuario.getId_tipo());
                ps.execute();
                
                ps.close();
            }
            
            rs.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al registrar Usuario: " + ex);
        }
        
        return datos_repetidos;
    }
    
    public boolean actualizarUsuario(String actualizar_usuario, String cod_usuario, int tipo) {
        canal = conectar.Conectar();
        
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        boolean datos_repetidos = false;
        
        String buscar_duplicado = "SELECT cod_usuario, id_tipo FROM usuario WHERE cod_usuario = '" + cod_usuario + "'";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar_duplicado);
            
            String actualizar = "UPDATE usuario SET cod_usuario='" + cod_usuario + "', id_tipo='" + tipo +
                        "' WHERE cod_usuario='" + actualizar_usuario + "'";
            
            if (rs.next()) {
                if (cod_usuario.equalsIgnoreCase(actualizar_usuario)) {
                    ps = canal.prepareStatement(actualizar);
                    ps.executeUpdate();
                    ps.close();
                } else {
                    datos_repetidos = true;
                }
            } else {
                ps = canal.prepareStatement(actualizar);
                ps.executeUpdate();
                ps.close();
            }
            
            rs.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al actualizar Usuario: " + ex);
        }
        
        return datos_repetidos;
    }
    
    public boolean eliminarUsuario(String usuario) {
        canal = conectar.Conectar();
        
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        boolean datos_eliminados = false;
        
        String buscar_usuario = "SELECT * FROM usuario WHERE cod_usuario='" + usuario + "'";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar_usuario);
            
            String eliminar = "DELETE FROM usuario WHERE cod_usuario='" + usuario + "'";
            
            if (rs.next()) {
                ps = canal.prepareStatement(eliminar);
                ps.executeUpdate();
                ps.close();
                datos_eliminados = true;
            }
            
            rs.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al Eliminar Usuario: " + ex);
        }
        
        return datos_eliminados;
    }
    
    // MÓDULO CLIENTES
    public boolean registrarCliente(String dni, int idTipoCliente, String nombre, String direccion, String n_contacto, String correo) {
        Cliente cliente = new Cliente(dni, idTipoCliente, nombre, direccion, n_contacto, correo);
        
        canal = conectar.Conectar();
        
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        boolean cliente_repetido = false;
        
        String buscar_duplicado = "SELECT * FROM cliente WHERE dni='" + dni + "'";
        String insertar = "INSERT INTO cliente (dni, id_tipo_cliente, nombre, direccion, n_contacto, correo) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar_duplicado);
            
            if (rs.next()) {
                cliente_repetido = true;
            } else {
                ps = canal.prepareStatement(insertar);
                ps.setString(1, cliente.getDni());
                ps.setInt(2, cliente.getIdTipoCliente());
                ps.setString(3, cliente.getNombre());
                ps.setString(4, cliente.getDireccion());
                ps.setString(5, cliente.getN_contacto());
                ps.setString(6, cliente.getCorreo());
                ps.execute();
                
                ps.close();
            }
       
            rs.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al Registrar Cliente: " + ex);
        }
        
        return cliente_repetido;
    }
    
    public int buscarIdTipoCliente(String tipoCliente) {
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        int id = 0;
        String buscar = "SELECT idTipo_cliente FROM tipo_cliente WHERE tipo_cliente = '" + tipoCliente + "'";

        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar);
            
            if (rs.next()) {
                id = rs.getInt(1);
                
                rs.close();
            }
            
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al buscar Nombre de Cliente: " + ex);
        }
        
        return id;
    }
    
    public Cliente buscarCliente(String buscar) {
        Cliente cliente = new Cliente();
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String mostrarContrato = "SELECT C.dni, T.tipo_cliente, C.nombre, C.direccion, C.n_contacto, C.correo " +
            "FROM cliente C INNER JOIN tipo_cliente T ON C.id_tipo_cliente = T.idTipo_cliente WHERE C.dni='" + buscar + "'";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(mostrarContrato);
            
            if (rs.next()) {
                cliente.setDni(rs.getString(1));
                cliente.setTipoCliente(rs.getString(2));
                cliente.setNombre(rs.getString(3));
                cliente.setDireccion(rs.getString(4));
                cliente.setN_contacto(rs.getString(5));
                cliente.setCorreo(rs.getString(6));
                
                rs.close();
            } else {
                cliente = null;
            }
            
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al buscar el Cliente: " + ex);
        }
        
        return cliente;
    }
    
    public boolean actualizarCliente(String actualizar_cliente, String dni, int idTipoCliente, String nombre, String direccion, String telefono, String correo) {
        canal = conectar.Conectar();
        
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        boolean datos_repetidos = false;
        
        String buscar_duplicado = "SELECT * FROM cliente WHERE dni='" + dni + "'";

        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar_duplicado);
            
            String actualizar = "UPDATE cliente SET dni='" + dni + "', id_tipo_cliente='" + idTipoCliente + "', nombre='" + nombre +
                    "', direccion='" + direccion + "', n_contacto='" + telefono + "', correo='" + correo +
                    "' WHERE dni = '" + actualizar_cliente + "'";
            
            if (rs.next()) {
                if (dni.equals(actualizar_cliente)) {
                    ps = canal.prepareStatement(actualizar);
                    ps.executeUpdate();
                    ps.close();
                } else {
                    datos_repetidos = true;
                }
            } else {
                ps = canal.prepareStatement(actualizar);
                ps.executeUpdate();
                ps.close();
            }
            
            rs.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al actualizar Cliente: " + ex);
        }
        
        return datos_repetidos;
    }
    
    public ArrayList<Cliente> tablaClientes() {
        ArrayList<Cliente> Datos_consultados = new  ArrayList();
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String consultar = "SELECT C.dni, T.tipo_cliente, C.nombre, C.direccion, C.n_contacto, C.correo FROM cliente C INNER JOIN tipo_cliente T on C.id_tipo_cliente = T.idTipo_cliente";
        
        try { 
            st =  canal.createStatement();
            rs =  st.executeQuery(consultar);
            
            while(rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setDni(rs.getString(1));
                cliente.setTipoCliente(rs.getString(2));
                cliente.setNombre(rs.getString(3));
                cliente.setDireccion(rs.getString(4));
                cliente.setN_contacto(rs.getString(5));
                cliente.setCorreo(rs.getString(6));
                
                Datos_consultados.add(cliente);
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        }  catch (SQLException ex) {
            System.out.println("Error al consultar Cliente: " + ex);
        }
        
        return Datos_consultados;
    }

    public boolean eliminarCliente(String dniCliente) {
        canal = conectar.Conectar();
        
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        boolean datos_eliminados = false;
        
        String buscar_contrato = "SELECT * FROM cliente WHERE dni='" + dniCliente + "'";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar_contrato);
            
            String eliminar = "DELETE FROM cliente WHERE dni='" + dniCliente + "'";
            
            if (rs.next()) {
                ps = canal.prepareStatement(eliminar);
                ps.executeUpdate();
                ps.close();
                datos_eliminados = true;
            }
            
            rs.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al Eliminar Cliente: " + ex);
        }
        
        return datos_eliminados;
    }
    
    // MÓDULO VIGILANTES
    public boolean registrarVigilante (String dni, String nombres, String apellidos, LocalDate fecha_nacimiento, String direccion, String telefono, LocalDate fecha_ingreso, boolean jefe_grupo, boolean disponible) { 
        Vigilante vigilante = new Vigilante(dni, nombres, apellidos, fecha_nacimiento, direccion, telefono, fecha_ingreso, jefe_grupo, disponible);
        
        canal = conectar.Conectar();     
        
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        boolean vigilante_repetido = false;
        
        String buscar_duplicado = "SELECT * FROM vigilante WHERE dni='" + dni + "'";
        String insertar = "INSERT INTO vigilante (dni, nombres, apellidos, fecha_nacimiento, direccion, n_contacto, fecha_ingreso, jefe_grupo, disponible) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar_duplicado);
         
            if (rs.next()) {
                vigilante_repetido = true;
            } else {
                ps = canal.prepareStatement(insertar);
                ps.setString(1, vigilante.getDni());
                ps.setString(2, vigilante.getNombres());
                ps.setString(3, vigilante.getApellidos());
                ps.setDate(4, Date.valueOf(vigilante.getFecha_nacimiento()));
                ps.setString(5, vigilante.getDireccion());
                ps.setString(6, vigilante.getTelefono());
                ps.setDate(7, Date.valueOf(vigilante.getFecha_ingreso()));
                ps.setBoolean(8, vigilante.isJefe_grupo());
                ps.setBoolean(9, vigilante.isDisponible());
                ps.execute();
                
                ps.close();
            }
       
            rs.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al Registrar Vigilante: " + ex);
        }
        
        return vigilante_repetido;
    }
    
    public Vigilante buscarVigilante(String buscar) {
        Vigilante vigilante = new Vigilante();
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String mostrarContrato = "SELECT * FROM vigilante WHERE dni = '" + buscar + "'";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(mostrarContrato);
            
            if (rs.next()) {
                vigilante.setDni(rs.getString(1));
                vigilante.setNombres(rs.getString(2));
                vigilante.setApellidos(rs.getString(3));
                vigilante.setFecha_nacimiento(rs.getDate(4).toLocalDate());
                vigilante.setDireccion(rs.getString(5));
                vigilante.setTelefono(rs.getString(6));
                vigilante.setFecha_ingreso(rs.getDate(7).toLocalDate());
                vigilante.setJefe_grupo(rs.getBoolean(8));
                vigilante.setDisponible(rs.getBoolean(9));
                
                rs.close();
            } else {
                vigilante = null;
            }
            
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al buscar el Vigilante: " + ex);
        }
        
        return vigilante;
    }
    
    public boolean actualizarVigilante(String actualizar_vigilante, String dni, String nombres, String apellidos, LocalDate fNacimiento, String direccion, String telefono, LocalDate fIngreso, boolean jefe_grupo, boolean disponible) {
        canal = conectar.Conectar();
        
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        boolean datos_repetidos = false;
        
        String buscar_duplicado = "SELECT dni FROM vigilante WHERE dni = '" + dni + "'";

        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar_duplicado);
            
            String actualizar = "UPDATE vigilante SET dni='" + dni + "', nombres='" + nombres + "', apellidos='" + apellidos +
                    "', fecha_nacimiento='" + Date.valueOf(fNacimiento) + "', direccion='" + direccion + "', n_contacto='" + telefono +
                    "', fecha_ingreso='" + Date.valueOf(fIngreso) + "', jefe_grupo='" + (jefe_grupo ? 1 : 0) + "', disponible='" + (disponible ? 1 : 0) +
                    "' WHERE dni = '" + actualizar_vigilante + "'";
            
            if (rs.next()) {
                if (dni.equals(actualizar_vigilante)) {
                    ps = canal.prepareStatement(actualizar);
                    ps.executeUpdate();
                    ps.close();
                } else {
                    datos_repetidos = true;
                }
            } else {
                ps = canal.prepareStatement(actualizar);
                ps.executeUpdate();
                ps.close();
            }
            
            rs.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al actualizar Vigilante: " + ex);
        }
        
        return datos_repetidos;
    }
    
    public ArrayList<Vigilante> tablaVigilantes() {
        ArrayList<Vigilante> Datos_consultados = new ArrayList<>();
        
        canal = conectar.Conectar();
        
        String consultar = "SELECT dni, nombres, apellidos, n_contacto, fecha_nacimiento, fecha_ingreso FROM vigilante";
        
        Statement st;
        ResultSet rs;
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(consultar);
            
            while(rs.next()) {
                Vigilante vigilante = new Vigilante();
                vigilante.setDni(rs.getString(1));
                vigilante.setNombres(rs.getString(2));
                vigilante.setApellidos(rs.getString(3));
                vigilante.setTelefono(rs.getString(4));
                vigilante.setFecha_nacimiento(rs.getDate(5).toLocalDate());
                vigilante.setFecha_ingreso(rs.getDate(6).toLocalDate());
                
                Datos_consultados.add(vigilante);
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al consultar Vigilante: " + ex);
        }
        
        return Datos_consultados;
    }
    
    // MÓDULO ROLES DE GUARDIA
    public ObservableList<String> cbTipoIncidencia() {
        ArrayList<String> datos = new ArrayList<>();
        ObservableList<String> tabla;
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String buscar = "SELECT * FROM tipo_incidencia";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(buscar);
            
            while(rs.next()) {
                if (rs.getInt(1) > 0)
                    datos.add(rs.getString(2));
            }
            
        } catch(SQLException ex) {
            System.out.println("Error en agregar variables de la BD al Controlador: " + ex);
        }
        
        tabla = FXCollections.observableList(datos);
        return tabla;
    }
    
    public boolean registrarRdG_VigilanteAusente(int codRdg, int codContrato, LocalDate fecha_rdg, String hora_inicio, String hora_final, String dni_vigilante, boolean asistencia, String hora_llegada, String hora_salida, int monto_rem) {
        Rol_de_Guardia _RdG = new Rol_de_Guardia(codRdg, codContrato, fecha_rdg, hora_inicio, hora_final, dni_vigilante, asistencia, hora_llegada, hora_salida, monto_rem);
        Beneficio _Beneficio = new Beneficio(0, 0, 0, codRdg);
        Incidencia _Incidencia = new Incidencia(codRdg, 0, "El vigilante no asistió al Rol de Guardia.");
        
        canal = conectar.Conectar();
        
        Statement stRdG, stBeneficio, stIncidencia;
        PreparedStatement psRdG, psBeneficio, psIncidencia;
        ResultSet rsRdG, rsBeneficio, rsIncidencia;
        
        boolean datos_repetidos = false;
        
        String buscar_duplicadoRdG = "SELECT idRdg FROM rol_de_guardia WHERE idRdg = '" + codRdg + "'";
        String insertarRdG = "INSERT INTO rol_de_guardia (idRdg, id_contrato, dni_vigilante, fecha_rdg, hora_inicio, hora_final, asistencia, hora_llegada, hora_salida, id_remuneracion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String buscar_duplicadoIncidencia = "SELECT * FROM incidencia WHERE id_rdg='" + _Incidencia.getIdRdg() + "' AND descripcion='" + _Incidencia.getDescripcion() +"'";
        String insertarIncidencia = "INSERT INTO incidencia (descripcion, id_tipo_incidencia, id_rdg) VALUES (?,?,?)";
        
        String buscar_duplicadoBeneficio = "SELECT * FROM beneficio WHERE id_rdg='" + _Beneficio.getIdRdg() + "' AND id_tipo_beneficio='" + _Beneficio.getId_tipo_beneficio() +"'";
        String insertarBeneficio = "INSERT INTO beneficio (id_tipo_beneficio, cantidad, monto, id_rdg) VALUES (?,?,?,?)";
        
        try {
            stRdG = canal.createStatement();
            rsRdG = stRdG.executeQuery(buscar_duplicadoRdG);
            
            stBeneficio = canal.createStatement();
            rsBeneficio = stBeneficio.executeQuery(buscar_duplicadoBeneficio);
            
            stIncidencia = canal.createStatement();
            rsIncidencia = stIncidencia.executeQuery(buscar_duplicadoIncidencia);
            
            if (rsRdG.next()) {
                datos_repetidos = true;
            } else {
                psRdG = canal.prepareStatement(insertarRdG);
                psRdG.setInt(1, _RdG.getIdRdg());
                psRdG.setInt(2, _RdG.getId_contrato());
                psRdG.setString(3, _RdG.getDni_vigilante());
                psRdG.setDate(4, Date.valueOf(_RdG.getFecha_rdg()));
                psRdG.setString(5, _RdG.getHora_inicio());
                psRdG.setString(6, _RdG.getHora_final());
                psRdG.setBoolean(7, _RdG.isAsistenciaV());
                psRdG.setString(8, _RdG.getHora_llegada());
                psRdG.setString(9, _RdG.getHora_salida());
                psRdG.setInt(10, _RdG.getId_remuneracion());
                psRdG.execute();
                
                psRdG.close();
                
                if (!rsBeneficio.next()) {
                    psBeneficio = canal.prepareStatement(insertarBeneficio);
                    psBeneficio.setInt(1, _Beneficio.getId_tipo_beneficio());
                    psBeneficio.setInt(2, _Beneficio.getCantidad());
                    psBeneficio.setDouble(3, _Beneficio.getMonto());
                    psBeneficio.setInt(4, _Incidencia.getIdRdg());
                    psBeneficio.execute();

                    psBeneficio.close();
                }
                
                if (!rsIncidencia.next()) {
                    psIncidencia = canal.prepareStatement(insertarIncidencia);
                    psIncidencia.setString(1, _Incidencia.getDescripcion());
                    psIncidencia.setInt(2, _Incidencia.getIdTipo_incidencia());
                    psIncidencia.setInt(3, _Incidencia.getIdRdg());
                    psIncidencia.execute();

                    psIncidencia.close();
                }
            }
            
            rsRdG.close();
            rsBeneficio.close();
            rsIncidencia.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al registrar Rol de Guardia con el Vigilante Ausente: " + ex);
        }
        
        return datos_repetidos;
    }
    
    public boolean registrarRdG(int codRdg, int codContrato, LocalDate fecha_rdg, String hora_inicio, String hora_final, String dni_vigilante, boolean asistencia, String hora_llegada, String hora_salida, int monto_rem) {
        Rol_de_Guardia _RdG = new Rol_de_Guardia(codRdg, codContrato, fecha_rdg, hora_inicio, hora_final, dni_vigilante, asistencia, hora_llegada, hora_salida, monto_rem);
        
        System.out.println("remuneracion: " + monto_rem);
        
        canal = conectar.Conectar();
        
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        boolean datos_repetidos = false;
        
        String buscar_duplicadoRdG = "SELECT idRdg FROM rol_de_guardia WHERE idRdg = '" + codRdg + "'";
        String insertarRdG = "INSERT INTO rol_de_guardia (idRdg, id_contrato, dni_vigilante, fecha_rdg, hora_inicio, hora_final, asistencia, hora_llegada, hora_salida, id_remuneracion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar_duplicadoRdG);
            
            if (rs.next()) {
                datos_repetidos = true;
            } else {
                ps = canal.prepareStatement(insertarRdG);
                ps.setInt(1, _RdG.getIdRdg());
                ps.setInt(2, _RdG.getId_contrato());
                ps.setString(3, _RdG.getDni_vigilante());
                ps.setDate(4, Date.valueOf(_RdG.getFecha_rdg()));
                ps.setString(5, _RdG.getHora_inicio());
                ps.setString(6, _RdG.getHora_final());
                ps.setBoolean(7, _RdG.isAsistenciaV());
                ps.setString(8, _RdG.getHora_llegada());
                ps.setString(9, _RdG.getHora_salida());
                ps.setInt(10, _RdG.getId_remuneracion());
                ps.execute();
                
                System.out.println("Modelo: " + _RdG.getId_remuneracion());
                
                ps.close();
            }
            
            rs.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al registrar Rol de Guardia: " + ex);
        }
        
        return datos_repetidos;
    }
    
    public void registrarBeneficios(ObservableList<Beneficio> beneficiosRegistrados) {
        canal = conectar.Conectar();
        
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        if (!beneficiosRegistrados.isEmpty()) {
            for (Beneficio _Beneficio: beneficiosRegistrados) {
                String buscar_duplicado = "SELECT * FROM beneficio WHERE id_rdg='" + _Beneficio.getIdRdg() + "' AND id_tipo_beneficio='" + _Beneficio.getId_tipo_beneficio() + "'";
                String insertar = "INSERT INTO beneficio (id_tipo_beneficio, cantidad, monto, id_rdg) VALUES (?,?,?,?)";
                
                try {
                    st = canal.createStatement();
                    rs = st.executeQuery(buscar_duplicado);

                    if (!rs.next()) {
                        ps = canal.prepareStatement(insertar);
                        ps.setInt(1, _Beneficio.getId_tipo_beneficio());
                        ps.setInt(2, _Beneficio.getCantidad());
                        ps.setDouble(3, _Beneficio.getMonto());
                        ps.setInt(4, _Beneficio.getId_Rdg());
                        
                        ps.execute();
                        
                        ps.close();
                    }
                    
                    rs.close();

                } catch (SQLException ex) {
                    System.out.println("Error al registrar Beneficio: " + ex);
                }
            }
        }
    }
    
    public Beneficio buscarTipoBeneficio(String tipoBeneficio) {
        Beneficio _beneficio = new Beneficio();
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String buscar = "SELECT idTipo_beneficio, costo FROM tipo_beneficio WHERE nombre_beneficio='" + tipoBeneficio + "'";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(buscar);
            
            if(rs.next()) {
                _beneficio.setId_tipo_beneficio(rs.getInt(1));
                _beneficio.setTipoBeneficio(rs.getString(2));
                _beneficio.setCosto(rs.getDouble(2));
                
                rs.close();
            }
            
        } catch(SQLException ex) {
            System.out.println("Error al buscar el Tipo de beneficio: " + ex);
        }
        
        return _beneficio;
    }
    
    public void registrarIncidencias(ObservableList<Incidencia> incidenciasRegistradas) {
        canal = conectar.Conectar();
        
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        if (!incidenciasRegistradas.isEmpty()) {
            for (Incidencia _Incidencia: incidenciasRegistradas) {
                String buscar_duplicado = "SELECT * FROM incidencia WHERE id_rdg='" + _Incidencia.getIdRdg() + "' AND descripcion='" + _Incidencia.getDescripcion() +"'";
                String insertar = "INSERT INTO incidencia (descripcion, id_tipo_incidencia, id_rdg) VALUES (?,?,?)";
                
                try {
                    st = canal.createStatement();
                    rs = st.executeQuery(buscar_duplicado);

                    if (!rs.next()) {
                        ps = canal.prepareStatement(insertar);
                        ps.setString(1, _Incidencia.getDescripcion());
                        ps.setInt(2, _Incidencia.getIdTipo_incidencia());
                        ps.setInt(3, _Incidencia.getIdRdg());
                        ps.execute();
                        
                        ps.close();
                    }
                    
                    rs.close();

                } catch (SQLException ex) {
                    System.out.println("Error al registrar Incidencia: " + ex);
                }
            }
        }
    }
    
    public int buscarIdTipoIncidencia(String tipoIncidencia) {
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        int id = 0;
        String buscar = "SELECT idTipo_incidencia FROM tipo_incidencia WHERE nombre_incidencia='" + tipoIncidencia + "'";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(buscar);
            
            if(rs.next()) {
                id = rs.getInt(1);
                
                rs.close();
            }
            
        } catch(SQLException ex) {
            System.out.println("Error al buscar el código del Tipo de incidencia: " + ex);
        }
        
        return id;
    }
    
    public String buscarTipoIncidencia(int idTipoIncidencia) {
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String tipoIncidencia = "";
        String buscar = "SELECT nombre_incidencia FROM tipo_incidencia WHERE idTipo_incidencia='" + idTipoIncidencia + "'";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(buscar);
            
            if(rs.next()) {
                tipoIncidencia = rs.getString(1);
                
                rs.close();
            }
            
        } catch(SQLException ex) {
            System.out.println("Error al buscar el Tipo de incidencia: " + ex);
        }
        
        return tipoIncidencia;
    }
    
    public boolean buscarIncidenciaYBeneficios_VigilanteAusente(int codRdG, int idTipoIncidencia, int idTipoBeneficio) {
        canal = conectar.Conectar();
        
        Statement stBeneficio, stIncidencia;
        ResultSet rsBeneficio, rsIncidencia;
        
        boolean ausente = false;
        
        String buscar_Incidencia = "SELECT * FROM incidencia WHERE id_rdg='" + codRdG + "' AND id_tipo_incidencia='" + idTipoIncidencia + "'";
        String buscar_Beneficio = "SELECT * FROM beneficio WHERE id_rdg='" + codRdG + "' AND id_tipo_beneficio='" + idTipoBeneficio + "'";
        
        try {
            stBeneficio = canal.createStatement();
            rsBeneficio = stBeneficio.executeQuery(buscar_Beneficio);
            
            stIncidencia = canal.createStatement();
            rsIncidencia = stIncidencia.executeQuery(buscar_Incidencia);
            
            if (rsBeneficio.next() && rsIncidencia.next()) {
                ausente = true;
                
                rsBeneficio.close();
                rsIncidencia.close();
            }
            
            stIncidencia.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al buscar Beneficios e Incidencias del RdG: " + ex);
        }
        
        return ausente;
    }
    
    public boolean eliminarIncidenciasYBeneficios_VigilanteAusente(int codRdG, int idTipoIncidencia, int idTipoBeneficio) {
        canal = conectar.Conectar();
        
        Statement stBeneficio, stIncidencia;
        PreparedStatement psBeneficio, psIncidencia;
        ResultSet rsBeneficio, rsIncidencia;
        
        boolean datos_eliminados = false;
        
        String buscar_Beneficio = "SELECT * FROM beneficio WHERE id_rdg='" + codRdG + "' AND id_tipo_beneficio='" + idTipoBeneficio + "'";
        String buscar_Incidencia = "SELECT * FROM incidencia WHERE id_rdg='" + codRdG + "' AND id_tipo_incidencia='" + idTipoIncidencia + "'";
        
        try {
            stBeneficio = canal.createStatement();
            stIncidencia = canal.createStatement();
            
            rsBeneficio = stBeneficio.executeQuery(buscar_Beneficio);
            rsIncidencia = stIncidencia.executeQuery(buscar_Incidencia);
            
            String eliminar_Beneficio = "DELETE FROM beneficio WHERE id_rdg='" + codRdG + "' AND id_tipo_beneficio='" + idTipoBeneficio + "'";
            String eliminar_Incidencia = "DELETE FROM incidencia WHERE id_rdg='" + codRdG + "' AND id_tipo_incidencia='" + idTipoIncidencia + "'";
            
            if (rsBeneficio.next() && rsIncidencia.next()) {
                psBeneficio = canal.prepareStatement(eliminar_Beneficio);
                psIncidencia = canal.prepareStatement(eliminar_Incidencia);
                
                psBeneficio.executeUpdate();
                psIncidencia.executeUpdate();
                
                psBeneficio.close();
                psIncidencia.close();
                
                datos_eliminados = true;
            }
            
            rsBeneficio.close();
            rsIncidencia.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al Eliminar Beneficios e Incidencias del RdG: " + ex);
        }
        
        return datos_eliminados;
    }
    
    public Rol_de_Guardia buscarRdG(String buscar) {
        Rol_de_Guardia _RdG = new Rol_de_Guardia();
        
        canal = conectar.Conectar();
        
        Statement stRdG, stIncidencia;
        ResultSet rsRdG, rsIncidencia;
        
        String mostrarRdG = "SELECT * FROM rol_de_guardia WHERE idRdg = '" + buscar + "'";
        String mostrarIncidencia = "SELECT I.id_tipo_incidencia, T.nombre_incidencia, I.descripcion FROM incidencia I INNER JOIN tipo_incidencia T ON I.id_tipo_incidencia=T.idTipo_incidencia WHERE id_rdg = '" + buscar + "'";
        
        try {
            stRdG = canal.createStatement();
            stIncidencia = canal.createStatement();
            
            rsRdG = stRdG.executeQuery(mostrarRdG);
            rsIncidencia = stIncidencia.executeQuery(mostrarIncidencia);
            
            if (rsRdG.next()) {
                _RdG.setIdRdg(rsRdG.getInt(1));
                _RdG.setId_contrato(rsRdG.getInt(2));
                _RdG.setDni_vigilante(rsRdG.getString(3));
                _RdG.setFecha_rdg(rsRdG.getDate(4).toLocalDate());
                _RdG.setHora_inicio(rsRdG.getString(5));
                _RdG.setHora_final(rsRdG.getString(6));
                _RdG.setAsistenciaV(rsRdG.getBoolean(7));
                _RdG.setHora_llegada(rsRdG.getString(8));
                _RdG.setHora_salida(rsRdG.getString(9));
                _RdG.setId_remuneracion(rsRdG.getInt(10));
                
                while(rsIncidencia.next()) {
                    Incidencia _Incidencia = new Incidencia();
                    
                    _Incidencia.setIdTipo_incidencia(rsIncidencia.getInt(1));
                    _Incidencia.setTipoIncidencia(rsIncidencia.getString(2));
                    _Incidencia.setDescripcion(rsIncidencia.getString(3));
                    
                    _RdG.agregarIncidencias(_Incidencia);
                }
                
                rsRdG.close();
                rsIncidencia.close();
            } else {
                _RdG = null;
            }
            
            stRdG.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al buscar el Rol de Guardia: " + ex);
        }
        
        return _RdG;
    }
    
    // MÓDULO CONTRATOS
    public ObservableList<Contrato> tablaContratos() {
        ArrayList<Contrato> datos = new ArrayList<>();
        ObservableList<Contrato> tabla;
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String mostrarContratos = "SELECT C.idContrato, C.dni_cliente, C.fecha_inicio, C.fecha_final, T.nombre_turno, C.hora_inicio, C.hora_final" +
                " FROM contrato C JOIN turno_contrato T WHERE C.id_turno = T.idTurno_contrato ORDER BY C.idContrato";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(mostrarContratos);
            
            while (rs.next()) {
                Contrato _contrato = new Contrato();
                _contrato.setIdContrato(rs.getInt(1));
                _contrato.setDni_cliente(rs.getString(2));
                _contrato.setFecha_inicio(rs.getDate(3).toLocalDate());
                _contrato.setFecha_final(rs.getDate(4).toLocalDate());
                _contrato.setTurno(rs.getString(5));
                _contrato.setHora_inicio(rs.getString(6));
                _contrato.setHora_final(rs.getString(7));
                
                datos.add(_contrato);
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al mostrar los Contratos en la Tabla: " + ex);
        }
        
        tabla = FXCollections.observableList(datos);
        return tabla;
    }
    
    public ObservableList<Contrato> consultarContrato(String buscar) {
        ArrayList<Contrato> datos = new ArrayList<>();
        ObservableList<Contrato> tabla;
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String mostrarContrato = "SELECT C.idContrato, C.dni_cliente, C.fecha_inicio, C.fecha_final, T.nombre_turno, C.hora_inicio, C.hora_final"
                + " FROM contrato C JOIN turno_contrato T WHERE C.id_turno = T.idTurno_contrato AND C.idContrato = '" + buscar + "'";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(mostrarContrato);
            
            if (rs.next()) {
                Contrato _contrato = new Contrato();
                _contrato.setIdContrato(rs.getInt(1));
                _contrato.setDni_cliente(rs.getString(2));
                _contrato.setFecha_inicio(rs.getDate(3).toLocalDate());
                _contrato.setFecha_final(rs.getDate(4).toLocalDate());
                _contrato.setTurno(rs.getString(5));
                _contrato.setHora_inicio(rs.getString(6));
                _contrato.setHora_final(rs.getString(7));
                
                datos.add(_contrato);
                rs.close();
            }
            
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al mostrar datos del Contrato en la Tabla: " + ex);
        }
        
        tabla = FXCollections.observableList(datos);
        return tabla;
    }
 
    public String buscarNombreCliente(String dniCliente) {
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String nombre = "";
        String buscar = "SELECT nombre FROM cliente WHERE dni = '" + dniCliente + "'";

        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar);
            
            if (rs.next()) {
                nombre = rs.getString(1);
                
                rs.close();
            }
            
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al buscar Nombre de Cliente: " + ex);
        }
        
        return nombre;
    }
    
    public int buscarTurno(String nombre_turno) {
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        int idTurno = 0;
        String buscar = "SELECT idTurno_contrato FROM turno_contrato WHERE nombre_turno LIKE '" + nombre_turno + "%'";

        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar);
            
            if (rs.next()) {
                idTurno = rs.getInt(1);
                
                rs.close();
            }
            
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al buscar Turno: " + ex);
        }
        
        return idTurno;
    }
    
    public boolean registrarContrato(int codContrato, String codCliente, LocalDate fInicio, LocalDate fCierre, int id_turno, String hInicio, String hFinal) {
        Contrato _contrato = new Contrato(codContrato, codCliente, fInicio, fCierre, id_turno, hInicio, hFinal);
        
        canal = conectar.Conectar();
        
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        boolean datos_repetidos = false;
        
        String buscar_duplicado = "SELECT idContrato FROM contrato WHERE idContrato = '" + codContrato + "'";
        String insertar = "INSERT INTO contrato (idContrato, dni_cliente, fecha_inicio, fecha_final, id_turno, hora_inicio, hora_final) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar_duplicado);
            
            if (rs.next()) {
                datos_repetidos = true;
            } else {
                ps = canal.prepareStatement(insertar);
                ps.setInt(1, _contrato.getIdContrato());
                ps.setString(2, _contrato.getDni_cliente());
                ps.setDate(3, Date.valueOf(_contrato.getFecha_inicio()));
                ps.setDate(4, Date.valueOf(_contrato.getFecha_final()));
                ps.setInt(5, _contrato.getId_turno());
                ps.setString(6, _contrato.getHora_inicio());
                ps.setString(7, _contrato.getHora_final());
                ps.execute();
                
                ps.close();
            }
            
            rs.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al registrar Contrato: " + ex);
        }
        
        return datos_repetidos;
    }
    
    public Contrato buscarContrato(String buscar) {
        Contrato contrato = new Contrato();
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String mostrarContrato = "SELECT C.idContrato, C.dni_cliente, C.fecha_inicio, C.fecha_final, T.nombre_turno, C.hora_inicio, C.hora_final"
                + " FROM contrato C JOIN turno_contrato T WHERE C.id_turno = T.idTurno_contrato AND C.idContrato = '" + buscar + "'";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(mostrarContrato);
            
            if (rs.next()) {
                contrato.setIdContrato(rs.getInt(1));
                contrato.setDni_cliente(rs.getString(2));
                contrato.setFecha_inicio(rs.getDate(3).toLocalDate());
                contrato.setFecha_final(rs.getDate(4).toLocalDate());
                contrato.setTurno(rs.getString(5));
                contrato.setHora_inicio(rs.getString(6));
                contrato.setHora_final(rs.getString(7));
                
                rs.close();
            } else {
                contrato = null;
            }
            
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al buscar el Contrato: " + ex);
        }
        
        return contrato;
    }
    
    public boolean actualizarContrato(int actualizar_contrato, int codContrato, String codCliente, LocalDate fInicio, LocalDate fCierre, int id_turno, String hInicio, String hCierre) {
        canal = conectar.Conectar();
        
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        boolean datos_repetidos = false;
        
        String buscar_duplicado = "SELECT idContrato FROM contrato WHERE idContrato = '" + codContrato + "'";

        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar_duplicado);
            
            String actualizar = "UPDATE contrato SET idContrato='" + codContrato + "', dni_cliente='" + codCliente +
                    "', fecha_inicio='" + Date.valueOf(fInicio) + "', fecha_final='" + Date.valueOf(fCierre) +
                    "', id_turno='" + id_turno + "', hora_inicio='" + hInicio + "', hora_final='" + hCierre +
                    "' WHERE idContrato = '" + actualizar_contrato + "'";
            
            if (rs.next()) {
                if (codContrato == actualizar_contrato) {
                    ps = canal.prepareStatement(actualizar);
                    ps.executeUpdate();
                    ps.close();
                } else {
                    datos_repetidos = true;
                }
            } else {
                ps = canal.prepareStatement(actualizar);
                ps.executeUpdate();
                ps.close();
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al actualizar Contrato: " + ex);
        }
        
        return datos_repetidos;
    }
    
    public boolean eliminarContrato(String codContrato) {
        canal = conectar.Conectar();
        
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        boolean datos_eliminados = false;
        
        String buscar_contrato = "SELECT * FROM contrato WHERE idContrato='" + codContrato + "'";
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar_contrato);
            
            String eliminar = "DELETE FROM contrato WHERE idContrato='" + codContrato + "'";
            
            if (rs.next()) {
                ps = canal.prepareStatement(eliminar);
                ps.executeUpdate();
                ps.close();
                
                datos_eliminados = true;
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al Eliminar Contrato: " + ex);
        }
        
        return datos_eliminados;
    }
    
    // MÓDULO CONTROL DE AUSENCIAS
    public ObservableList<Rol_de_Guardia> tablaMostrarAusencias(String dni_vigilante) {
        ArrayList<Rol_de_Guardia> datos = new ArrayList<>();
        ObservableList<Rol_de_Guardia> tabla;
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String mostrarRdG_Ausencia = "SELECT r.idRdg, r.id_contrato, r.fecha_rdg, rem.monto, r.asistencia FROM rol_de_guardia r INNER JOIN remuneracion_rdg rem ON r.id_remuneracion = rem.idRemuneracion_rdg WHERE r.dni_vigilante ='" + dni_vigilante + "'";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(mostrarRdG_Ausencia);
            
            while(rs.next()) {
                if(rs.getBoolean(5) == false) {
                    Rol_de_Guardia _Rol = new Rol_de_Guardia();
                    
                    _Rol.setIdRdg(rs.getInt(1));
                    _Rol.setId_contrato((rs.getInt(2)));
                    _Rol.setFecha_rdg(rs.getDate(3).toLocalDate());
                    _Rol.setMonto_rem(rs.getDouble(4));
                    
                    datos.add(_Rol);
                } 
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        }catch(SQLException ex){
            System.out.println("Error al mostrar las Ausencias en la Tabla de Control de Ausencias: " + ex);
        }
        
        tabla = FXCollections.observableList(datos);
        return tabla;        
    }

    public int ContadorTrabajosAsignados(String datos_Ausencias) {
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        int contTrabajos = 0;
        String _BuscarRdg = "SELECT rol_de_guardia.idRdg FROM rol_de_guardia WHERE dni_vigilante='" + datos_Ausencias + "'";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(_BuscarRdg);
            
            while(rs.next()){
                contTrabajos++;
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        }catch(SQLException ex){
            System.out.println("Error al contar los trabajos asignados del Vigilante: " + ex);
        }
        
        return contTrabajos;
    }
    
    public int ContadorAusenciasIdentificador(String dni_vigilante) {
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        int contAusencia = 0;
        String _BuscarRdg = "SELECT rol_de_guardia.asistencia FROM rol_de_guardia WHERE dni_vigilante='" + dni_vigilante + "'";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(_BuscarRdg);
            
            while(rs.next()){
                if(rs.getBoolean(1) == false){
                    contAusencia++;
                }
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        } catch(SQLException ex) {
            System.out.println("Error al contar las Ausencias del Vigilante: " + ex);
        }
        
        return contAusencia;
    }
    
    public ObservableList<Rol_de_Guardia> tablaMostrarAusenciasMes(String BuscarVig, int datos_Mes){
        ArrayList<Rol_de_Guardia> datos = new ArrayList<>();
        ObservableList<Rol_de_Guardia> tabla;
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String _BuscarRdg = "SELECT r.idRdg, r.id_contrato, r.fecha_rdg, rem.monto, r.asistencia FROM rol_de_guardia r INNER JOIN remuneracion_rdg rem ON r.id_remuneracion = rem.idRemuneracion_rdg WHERE r.dni_vigilante ='" + BuscarVig + "' AND MONTH(fecha_rdg)='" + datos_Mes + "'";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(_BuscarRdg);
            
            while(rs.next()){
                if(rs.getBoolean(5) == false){
                    Rol_de_Guardia _Rol = new Rol_de_Guardia();
                    
                    _Rol.setIdRdg(rs.getInt(1));
                    _Rol.setId_contrato((rs.getInt(2)));
                    _Rol.setFecha_rdg(rs.getDate(3).toLocalDate());
                    _Rol.setMonto_rem(rs.getDouble(4));

                    datos.add(_Rol);
                }
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        } catch(SQLException ex) {
            System.out.println("Error al mostrar las Ausencias por Mes en la Tabla de Control de Ausencias: " + ex);
        }
        
        tabla = FXCollections.observableList(datos);
        return tabla;        
    }
    
    public int ContadorTrabajosAsignadosConMes(String dni_vigilante, int datos_Mes){
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        int contTrabajos = 0;
        String _BuscarRdg = "SELECT rol_de_guardia.idRdg FROM rol_de_guardia WHERE dni_vigilante='" + dni_vigilante + "'AND MONTH(fecha_rdg)='" + datos_Mes +"'";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(_BuscarRdg);
            
            while(rs.next()){
                contTrabajos++; 
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        }catch(SQLException ex){
            System.out.println("Error al contar los trabajos asignados en el Mes al Vigilante: " + ex);
        }
        
        return contTrabajos;
    }
    
    public int ContadorAusenciasConMes(String dni_vigilante, int datos_Mes) {
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        int contAusencia = 0;
        String _BuscarRdg = "SELECT rol_de_guardia.asistencia FROM rol_de_guardia WHERE dni_vigilante='" + dni_vigilante + "'AND MONTH(fecha_rdg)='" + datos_Mes + "'";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(_BuscarRdg);
            
            while(rs.next()){
                if(rs.getBoolean(1) == false){
                    contAusencia++;
                }
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        }catch(SQLException ex){
            System.out.println("Error al contar las Ausencias del Mes del Vigilante: " + ex);
        }
        
        return contAusencia;
    }
    
    // MÓDULO SALARIO Y BENEFICIOS
    public ObservableList<Rol_de_Guardia> tablaMostrarRolEnBeneficios(String dni_vigilante) {
        ArrayList<Rol_de_Guardia> datos = new ArrayList<>();
        ObservableList<Rol_de_Guardia> tabla;
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String mostrarRdG = "SELECT r.idRdg, r.fecha_rdg, r.asistencia, rem.monto from rol_de_guardia r INNER JOIN remuneracion_rdg rem ON r.id_remuneracion=rem.idRemuneracion_rdg WHERE r.dni_vigilante='" + dni_vigilante + "'";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(mostrarRdG);
            
            while(rs.next()){
                Rol_de_Guardia _RolB = new Rol_de_Guardia();
                
                _RolB.setIdRdg(rs.getInt(1));
                _RolB.setFecha_rdg(rs.getDate(2).toLocalDate());
                
                if (rs.getBoolean(3)) {
                    _RolB.setAsistencia('S');
                } else {
                    _RolB.setAsistencia('N');
                }
                
                _RolB.setMonto_rem(rs.getDouble(4));
                
                datos.add(_RolB);
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        }catch(SQLException ex){
            System.out.println("Error en Tabla de Rol de Guardia en Salario y Beneficios: " + ex);
        }
        
        tabla = FXCollections.observableList(datos);
        return tabla;
    }
    
    public ObservableList<Beneficio> tablaMostrarBeneficios(String dni_vigilante) {
        ArrayList<Beneficio> datos = new ArrayList<>();
        ObservableList<Beneficio> tabla;
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String Rdg_Beneficios = "SELECT r.idRdg, b.idBeneficio, t.nombre_beneficio, t.costo, b.cantidad, b.monto FROM rol_de_guardia r INNER JOIN beneficio b ON r.idrdg=b.id_Rdg JOIN tipo_beneficio t ON b.id_tipo_beneficio=t.idTipo_beneficio WHERE r.dni_vigilante ='" + dni_vigilante + "'";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(Rdg_Beneficios);
            
            while(rs.next()){
                Beneficio _Ben = new Beneficio();
                
                _Ben.setId_Rdg(rs.getInt(1));
                _Ben.setId_tipo_beneficio(rs.getInt(2));
                _Ben.setTipoBeneficio(rs.getString(3));
                _Ben.setCosto(rs.getDouble(4));
                _Ben.setCantidad(rs.getInt(5));
                _Ben.setMonto(rs.getDouble(6));
                
                datos.add(_Ben);
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        }catch(SQLException ex){
            System.out.println("Error en Tabla de Beneficios en Salario y Beneficios: " + ex);
        }
        
        tabla = FXCollections.observableList(datos);
        return tabla;
    }
    
    // MÓDULO ROTACIÓN DEL PERSONAL
    public ObservableList<Rol_de_Guardia> tablaMostrarSinFiltros_RotacionP() {
        ArrayList<Rol_de_Guardia> datos = new ArrayList<>();
        ObservableList<Rol_de_Guardia> tabla;
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String BuscarRolGuardia = "SELECT dni_vigilante, idRdg, fecha_rdg, hora_inicio, hora_final FROM rol_de_guardia";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(BuscarRolGuardia);
            
            while(rs.next()){
                Rol_de_Guardia _RolB = new Rol_de_Guardia();
                
                _RolB.setDni_vigilante(rs.getString(1));
                _RolB.setIdRdg(rs.getInt(2));
                _RolB.setFecha_rdg(rs.getDate(3).toLocalDate());
                _RolB.setHora_inicio(rs.getString(4));
                _RolB.setHora_final(rs.getString(5));
                
                datos.add(_RolB);
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        }catch(SQLException ex){
            System.out.println("Error en Tabla sin filtros de Rotación del Personal: " + ex);
        }
        
        tabla = FXCollections.observableList(datos);
        return tabla;
    }
    
    public ObservableList<Rol_de_Guardia> tablaMostrarPorTurno(String Turno) {
        ArrayList<Rol_de_Guardia> datos = new ArrayList<>();
        ObservableList<Rol_de_Guardia> tabla;
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String mostrarPorTurno = "SELECT r.dni_vigilante, r.idRdg, r.fecha_rdg, r.hora_inicio, r.hora_final FROM rol_de_guardia r "
                + "INNER JOIN contrato c ON r.id_contrato = c.idContrato JOIN turno_contrato t ON c.id_turno = t.idTurno_contrato WHERE t.nombre_turno='" + Turno + "'";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(mostrarPorTurno);
            
            while(rs.next()){
                Rol_de_Guardia _RolB = new Rol_de_Guardia();
                
                _RolB.setDni_vigilante(rs.getString(1));
                _RolB.setIdRdg(rs.getInt(2));
                _RolB.setFecha_rdg(rs.getDate(3).toLocalDate());
                _RolB.setHora_inicio(rs.getString(4));
                _RolB.setHora_final(rs.getString(5));
                
                datos.add(_RolB);
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        }catch(SQLException ex){
            System.out.println("Error al mostrar por turno la Rotación del Personal: " + ex);
        }
        
        tabla = FXCollections.observableList(datos);
        return tabla;
    }
    
    public ObservableList<Rol_de_Guardia> tablaMostrarPorTurnoYFechas(String Turno, LocalDate fecha_desde, LocalDate fecha_hasta) {
        ArrayList<Rol_de_Guardia> datos = new ArrayList<>();
        ObservableList<Rol_de_Guardia> tabla;
        
        canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        
        String Buscar = "SELECT r.dni_vigilante, r.idRdg, r.fecha_rdg, r.hora_inicio, r.hora_final FROM rol_de_guardia r "
                + "INNER JOIN contrato c ON r.id_contrato = c.idContrato JOIN turno_contrato t ON c.id_turno = t.idTurno_contrato WHERE t.nombre_turno='" + Turno
                + "' AND r.fecha_rdg BETWEEN '" + Date.valueOf(fecha_desde) + "' AND '"+ Date.valueOf(fecha_hasta) + "'";
        
        try{
            st = canal.createStatement();
            rs = st.executeQuery(Buscar);
            
            while(rs.next()){
                Rol_de_Guardia _RolB = new Rol_de_Guardia();
                
                _RolB.setDni_vigilante(rs.getString(1));
                _RolB.setIdRdg(rs.getInt(2));
                _RolB.setFecha_rdg(rs.getDate(3).toLocalDate());
                _RolB.setHora_inicio(rs.getString(4));
                _RolB.setHora_final(rs.getString(5));
                
                datos.add(_RolB);
            }
            
            rs.close();
            st.close();
            conectar.Desconectar();
            
        }catch(SQLException ex){
            System.out.println("Error al mostrar por turno y fechas la Rotación del Personal: " + ex);
        } 
        tabla= FXCollections.observableArrayList(datos);
        return tabla;
    }
    
    // BITÁCORA
    public DatosBitacora Tipos_combobox_Bitcora(){
        
                canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        String buscar = "select accion_bitacora.nombre_accion from accion_bitacora";
        String acciones_bitacora;
    
        ArrayList<String> acciones_bitacora_Array = new ArrayList<>();
        DatosBitacora datobitacora = new DatosBitacora();
        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar);
            try{
            while (rs.next()) {
               acciones_bitacora  = rs.getString(1);
               acciones_bitacora_Array.add(acciones_bitacora);
            }
            }catch(Exception e){
                System.out.println("Error en cargar los datos de la bd");
            }
            
           
            datobitacora = new DatosBitacora(acciones_bitacora_Array);
            st.close();
            rs.close();
            conectar.Desconectar();

        } catch (SQLException ex) {
            System.out.println("Error al buscar Usuario: " + ex);
        }
        
        return datobitacora;
    }
    public  ArrayList<DatosBitacora> consultar_bitacora (LocalDate Fecha_inicio,LocalDate Fecha_Final,String Codigo_de_busqueda,ObservableList<Integer> tipo_busqueda,boolean busqueda_por_codigo){
    canal = conectar.Conectar();
        
    ArrayList<DatosBitacora> Datos_consultados = null;
    DatosBitacora bitacora = new DatosBitacora();

    try{
        String Consulta_sql ="select usuario.nombre,accion_bitacora.nombre_accion,accion_bitacora.descripcion,bitacora.fecha \n" +
            "from bitacora inner join usuario on bitacora.cod_usuario= usuario.cod_usuario\n" +
            "inner join accion_bitacora on accion_bitacora.idAccion_bitacora = bitacora.id_accion\n" +
            " where (bitacora.fecha >='"+Fecha_inicio.toString()+"' and bitacora.fecha <= '"+Fecha_Final.toString()+"')";
        
        if(busqueda_por_codigo){
         Consulta_sql += "and (usuario.idusuario = '"+Codigo_de_busqueda+"')"; 
            }
            Consulta_sql += "and (";
            boolean PrimeraVez= false;
        for (Integer p: tipo_busqueda) {
            if(PrimeraVez==false){
                Consulta_sql +=  "bitacora.id_accion = "+(p+1)+" ";
                PrimeraVez=true;
                }else{
                    Consulta_sql +=  "|| bitacora.id_accion = "+(p+1)+" ";
                }
            }
            Consulta_sql += ")";
        try{
            Datos_consultados= bitacora.Consultar_Funcion(Consulta_sql,canal);
            }catch(Exception e) {Mensajes.Mensaje("Error de consulta 3 " + e);}
        }catch(Exception e){}
    return Datos_consultados;
    } 
    
    public Transacciones Tipos_combobox_Transaccion(){
        
                canal = conectar.Conectar();
        
        Statement st;
        ResultSet rs;
        String buscar_metodop_transaccion = "SELECT metodop_transaccion.nombre_metodo from metodop_transaccion";
        String buscar_estado_transaccion = "select estado_transaccion.estado_t from estado_transaccion";
        String buscar_tipo_transaccion = "SELECT tipo_transaccion.nombre_tipo from tipo_transaccion";
        String metodo_transaccion,estado_transaccion,tipo_transaccion;
        ArrayList<String> datos_metodo_transaccion = new ArrayList<>();
        ArrayList<String> datos_estado_transaccion = new ArrayList<>();
        ArrayList<String> datos_tipo_transaccion = new ArrayList<>();
        Transacciones transacciones = new Transacciones();
        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar_metodop_transaccion);
            try{
            while (rs.next()) {
               metodo_transaccion  = rs.getString(1);
               datos_metodo_transaccion.add(metodo_transaccion);
            }
            }catch(Exception e){
                System.out.println("Error en cargar los datos de la bd");
            }
            
            rs = st.executeQuery(buscar_estado_transaccion);
            try{
            while (rs.next()) {
               estado_transaccion  = rs.getString(1);
               datos_estado_transaccion.add(estado_transaccion);
            }
            }catch(Exception e){
                System.out.println("Error en cargar los datos de la bd");
            }
            
            rs = st.executeQuery(buscar_tipo_transaccion);
            try{
            while (rs.next()) {
               tipo_transaccion  = rs.getString(1);
               datos_tipo_transaccion.add(tipo_transaccion);
            }
            }catch(Exception e){
                System.out.println("Error en cargar los datos de la bd");
            }
            
            transacciones = new Transacciones(datos_metodo_transaccion,datos_estado_transaccion,datos_tipo_transaccion);
            st.close();
            rs.close();
            conectar.Desconectar();

        } catch (SQLException ex) {
            System.out.println("Error al buscar Usuario: " + ex);
        }
        return transacciones;
        
      
    }
    public Transacciones Datos_Transacciones(int cod_rol){
       // canal = conectar.Conectar()
        Transacciones Datos_de_BD = new Transacciones ();
        canal =conectar.Conectar();
        try { 
         
            String Consulta_sql ="SELECT rol_de_guardia.fecha_rdg,contrato.idContrato,contrato.fecha_inicio,cliente.dni,cliente.nombre,vigilante.dni,vigilante.nombres from rol_de_guardia INNER JOIN contrato on rol_de_guardia.id_contrato= contrato.idContrato INNER join cliente on contrato.dni_cliente = cliente.dni INNER join vigilante on rol_de_guardia.dni_vigilante = vigilante.dni "
                    + "WHERE rol_de_guardia.idRdg = "+ cod_rol+"";
            String fecha_rdg,Codigo_contrato,fecha__inicio_contrato,dni_cliente, nombre_cliente, dni_vigilante,nombre_vigilante;
            Statement statement;
            statement =  canal.createStatement();  //Contiene nuestro conector
            ResultSet resultado =  statement.executeQuery(Consulta_sql);    //Contiene nuestra variable statement y ejecuta el select from desde la base de datos
   
            while(resultado.next()){
                try{
                    fecha_rdg = resultado.getString(1);    
                    Codigo_contrato = resultado.getString(2);
                    fecha__inicio_contrato = resultado.getString(3);
                    dni_cliente = resultado.getString(4);
                    nombre_cliente = resultado.getString(5);
                    dni_vigilante = resultado.getString(6);
                    nombre_vigilante = resultado.getString(7);
                  
                    Datos_de_BD = new Transacciones(fecha_rdg, Codigo_contrato, fecha__inicio_contrato, dni_cliente, nombre_cliente, dni_vigilante,nombre_vigilante);

                        }catch(SQLException e){System.out.println("ERORR");}
                }
            resultado.close();
            statement.close();
            } catch (SQLException ex) {System.out.println("ERORR");}
        
        conectar.Desconectar(); 
        return  Datos_de_BD;
    }
    
    public String Resgistro_Transaccion_pago(Transacciones transaccion){
        canal =conectar.Conectar();
        String texto_validacion = "Error en los siguientes campos:";
        String buscar_duplicado = "SELECT * FROM transaccion WHERE transaccion.id_rdg ="+ transaccion.getRdg()+"";
        String Existencia_del_rol = "SELECT * FROM rol_de_guardia WHERE ((rol_de_guardia.id_contrato = "+transaccion.getContrato()+") &&( rol_de_guardia.idRdg = "+transaccion.getRdg()+"))";
        String insertar = "INSERT INTO transaccion( `id_tipo_transaccion`, `id_rdg`, `fecha_trabajo`, `monto_t`, `id_estado`, `id_metodo_pago`, `fecha_cancelada`, `referencia`) VALUES (?,?,?,?,?,?,?,?)";
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        boolean rol_de_guardia_registrado = false, contrato_igual_rol_rdg = false;
     
        
        try {
            st = canal.createStatement();
            rs = st.executeQuery(buscar_duplicado);
            
                if (rs.next()) {
                    rol_de_guardia_registrado = false; texto_validacion+= "\nEl rol ya se encuentra asignado a un transaccion"; 
                    }else rol_de_guardia_registrado = true; 
                
                rs = st.executeQuery(Existencia_del_rol);
            
                if (rs.next()) {
                    contrato_igual_rol_rdg = true; 
                    }else{ contrato_igual_rol_rdg = false;texto_validacion+= "\nEl rol del guardia es diferente al contrato a asignado"; }  
            
                if(rol_de_guardia_registrado==true&&contrato_igual_rol_rdg==true){
                    ps = canal.prepareStatement(insertar);
                    ps.setInt(1, Integer.parseInt(transaccion.getTipodepago()));
                    ps.setInt(2, Integer.parseInt(transaccion.getRdg()));
                    ps.setDate(3, Date.valueOf(transaccion.getDiaRealizado()));
                    ps.setDouble(4, Double.parseDouble(transaccion.getMonto()));
                    ps.setInt(5,Integer.parseInt(transaccion.getEstadoPago()));
                    ps.setInt(6, Integer.parseInt(transaccion.getMetodoPago()));
                    ps.setDate(7, Date.valueOf(transaccion.getDiaPagado()));    
                    ps.setString(8, transaccion.getReferencia());
                    ps.execute();
                    ps.close();
                    texto_validacion="Transaccion registrada exitosamente ";
                    }   
       
            rs.close();
            conectar.Desconectar();
                }catch (SQLException ex) {
                    System.out.println("Error al Registrar Trnasaccion: " + ex);
                    }
        
        return texto_validacion;
    }
    
     public void registrarBitacora (String Usuario,int Codigo_accion) {
        
        
        canal = conectar.Conectar();
        Calendar c1 = Calendar.getInstance();
        String fecha= ""+c1.get(Calendar.YEAR) +":"+(c1.get(Calendar.MONTH)+1)+":"+c1.get(Calendar.DATE)+"";
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        
        boolean cliente_repetido = false;
        
        String insertar = "INSERT INTO bitacora( `cod_usuario`, `id_accion`, `fecha`) VALUES (?,?,?)";
        
        try {
            st = canal.createStatement();
            ps = canal.prepareStatement(insertar);
            
               ps.setString(1, Usuario);
                ps.setInt(2, Codigo_accion);
                ps.setString(3, fecha);
  
                ps.execute();
                
                ps.close();
            
       
            
            conectar.Desconectar();
            
        } catch (SQLException ex) {
            System.out.println("Error al Registrar Bitacora: " + ex);
        }
      
    }
}
