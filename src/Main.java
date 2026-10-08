import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner entrada = new Scanner(System.in);
    private static final List<Paciente> pacientes = new ArrayList<>();
    private static final List<ProfesionalSalud> profesionales = new ArrayList<>();
    private static final List<EquipoMedico> equipos = new ArrayList<>();
    private static final List<ServicioDomiciliario> servicios = new ArrayList<>();

    public static void main(String[] args) {
        boolean continuar = true;

        System.out.println("=== MediHome - Gestion de servicios medicos domiciliarios ===");
        while (continuar) {
            mostrarMenu();
            switch (leerEntero("Seleccione una opcion: ", 0, 7)) {
                case 1:
                    registrarPaciente();
                    break;
                case 2:
                    registrarProfesional();
                    break;
                case 3:
                    crearEquipoMedico();
                    break;
                case 4:
                    programarServicio();
                    break;
                case 5:
                    registrarAtencion();
                    break;
                case 6:
                    listarInformacion();
                    break;
                case 7:
                    enviarNotificacion();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("Gracias por utilizar MediHome.");
                    break;
                default:
                    break;
            }
        }
        entrada.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menu principal ---");
        System.out.println("1. Registrar paciente");
        System.out.println("2. Registrar profesional de salud");
        System.out.println("3. Crear equipo medico");
        System.out.println("4. Programar servicio domiciliario");
        System.out.println("5. Registrar atencion y signos vitales");
        System.out.println("6. Consultar informacion");
        System.out.println("7. Enviar notificacion");
        System.out.println("0. Salir");
    }

    private static void registrarPaciente() {
        System.out.println("\n--- Registro de paciente ---");
        String identificacion = leerIdentificacionUnica();
        Paciente paciente = new Paciente();
        paciente.setIdentificacion(identificacion);
        paciente.setNombre(leerTexto("Nombre completo: "));
        paciente.setCorreo(leerTexto("Correo electronico: "));
        paciente.setTelefono(leerTexto("Telefono: "));
        paciente.setDireccionPrincipal(leerTexto("Direccion principal: "));
        pacientes.add(paciente);
        System.out.println("Paciente registrado correctamente.");
        paciente.notificar("Bienvenido a MediHome, " + paciente.getNombre() + ".");
    }

    private static void registrarProfesional() {
        System.out.println("\n--- Registro de profesional de salud ---");
        String identificacion = leerIdentificacionUnica();
        String nombre = leerTexto("Nombre completo: ");
        String correo = leerTexto("Correo electronico: ");
        String numeroRegistro = leerNumeroRegistroUnico();
        ProfesionalSalud profesional = new ProfesionalSalud(numeroRegistro);
        profesional.setIdentificacion(identificacion);
        profesional.setNombre(nombre);
        profesional.setCorreo(correo);
        profesionales.add(profesional);
        System.out.println("Profesional registrado correctamente.");
    }

    private static void crearEquipoMedico() {
        System.out.println("\n--- Creacion de equipo medico ---");
        if (profesionales.isEmpty()) {
            System.out.println("Primero debe registrar al menos un profesional.");
            return;
        }

        EquipoMedico equipo = new EquipoMedico();
        equipo.setCodigo(leerCodigoEquipoUnico());
        equipo.setNombre(leerTexto("Nombre del equipo: "));
        equipo.setZonaCobertura(leerTexto("Zona de cobertura: "));
        equipo.setProfesionales(new ArrayList<>());

        boolean agregarOtro = true;
        while (agregarOtro) {
            ProfesionalSalud profesional = seleccionarProfesional();
            if (!equipo.getProfesionales().contains(profesional)) {
                equipo.agregarProfesional(profesional);
                System.out.println("Profesional agregado al equipo.");
            } else {
                System.out.println("Ese profesional ya pertenece al equipo.");
            }
            agregarOtro = leerSiNo("Desea agregar otro profesional? (s/n): ");
        }

        equipos.add(equipo);
        System.out.println("Equipo medico creado correctamente.");
    }

    private static void programarServicio() {
        System.out.println("\n--- Programacion de servicio domiciliario ---");
        if (pacientes.isEmpty() || profesionales.isEmpty()) {
            System.out.println("Debe registrar al menos un paciente y un profesional.");
            return;
        }

        ServicioDomiciliario servicio = new ServicioDomiciliario();
        servicio.setCodigoUnico("SD-" + (servicios.size() + 1));
        servicio.setFechaHora(LocalDateTime.now());
        servicio.setPaciente(seleccionarPaciente());
        servicio.setProfesionalSalud(seleccionarProfesional());
        servicio.setDireccionAtencion(leerTexto("Direccion de atencion: "));
        servicio.setMotivo(leerTexto("Motivo de la visita: "));
        servicio.setEstado("Programado");
        servicios.add(servicio);

        System.out.println("Servicio " + servicio.getCodigoUnico() + " programado correctamente.");
        servicio.getPaciente().notificar("Su servicio " + servicio.getCodigoUnico()
                + " ha sido programado para " + servicio.getFechaHora() + ".");
        servicio.getProfesionalSalud().notificar("Tiene asignado el servicio "
                + servicio.getCodigoUnico() + " para el paciente "
                + servicio.getPaciente().getNombre() + ".");
    }

    private static void registrarAtencion() {
        System.out.println("\n--- Registro de atencion medica ---");
        ServicioDomiciliario servicio = seleccionarServicioSinAtencion();
        if (servicio == null) {
            return;
        }

        AtencionMedica atencion = new AtencionMedica();
        atencion.setFechaInicio(LocalDateTime.now());
        atencion.setObservaciones(leerTexto("Observaciones de la atencion: "));
        atencion.setRecomendaciones(leerTexto("Recomendaciones para el paciente: "));

        List<MedicionSignos> mediciones = new ArrayList<>();
        boolean agregarMedicion = true;
        while (agregarMedicion) {
            MedicionSignos medicion = new MedicionSignos();
            medicion.setFechaHora(LocalDateTime.now());
            medicion.setTemperatura(leerDecimal("Temperatura (C): ", 20, 45));
            medicion.setFrecuenciaCardiaca(leerEntero("Frecuencia cardiaca (lpm): ", 1, 300));
            medicion.setPresionSistolica(leerEntero("Presion sistolica (mmHg): ", 1, 300));
            medicion.setPresionDiastolica(leerEntero("Presion diastolica (mmHg): ", 1, 200));
            medicion.setSaturacionOxigeno(leerDecimal("Saturacion de oxigeno (%): ", 0, 100));
            mediciones.add(medicion);
            agregarMedicion = leerSiNo("Desea agregar otra medicion? (s/n): ");
        }

        atencion.setMedicionSignos(mediciones);
        atencion.setFechaFin(LocalDateTime.now());
        servicio.setAtencionMedica(atencion);
        servicio.setEstado("Atendido");
        System.out.println("Atencion registrada para el servicio " + servicio.getCodigoUnico() + ".");
        servicio.getPaciente().notificar("Su atencion ha sido registrada. Recomendaciones: "
                + atencion.getRecomendaciones());
    }

    private static void listarInformacion() {
        System.out.println("\n--- Informacion registrada ---");

        System.out.println("\nPacientes:");
        if (pacientes.isEmpty()) {
            System.out.println("  No hay pacientes registrados.");
        }
        for (Paciente paciente : pacientes) {
            System.out.println("  " + paciente.getIdentificacion() + " | " + paciente.getNombre()
                    + " | " + paciente.getTelefono() + " | " + paciente.getDireccionPrincipal());
        }

        System.out.println("\nProfesionales:");
        if (profesionales.isEmpty()) {
            System.out.println("  No hay profesionales registrados.");
        }
        for (ProfesionalSalud profesional : profesionales) {
            System.out.println("  " + profesional.getIdentificacion() + " | " + profesional.getNombre()
                    + " | Registro: " + profesional.getNumeroRegistroProfesional());
        }

        System.out.println("\nEquipos medicos:");
        if (equipos.isEmpty()) {
            System.out.println("  No hay equipos registrados.");
        }
        for (EquipoMedico equipo : equipos) {
            System.out.println("  " + equipo.getCodigo() + " | " + equipo.getNombre()
                    + " | Zona: " + equipo.getZonaCobertura());
            for (ProfesionalSalud profesional : equipo.getProfesionales()) {
                System.out.println("    - " + profesional.getNombre());
            }
        }

        System.out.println("\nServicios domiciliarios:");
        if (servicios.isEmpty()) {
            System.out.println("  No hay servicios registrados.");
        }
        for (ServicioDomiciliario servicio : servicios) {
            System.out.println("  " + servicio.getCodigoUnico() + " | " + servicio.getEstado()
                    + " | " + servicio.getFechaHora() + " | Paciente: "
                    + servicio.getPaciente().getNombre() + " | Profesional: "
                    + servicio.getProfesionalSalud().getNombre() + " | Motivo: " + servicio.getMotivo());
            if (servicio.getAtencionMedica() != null) {
                AtencionMedica atencion = servicio.getAtencionMedica();
                System.out.println("    Observaciones: " + atencion.getObservaciones());
                System.out.println("    Recomendaciones: " + atencion.getRecomendaciones());
                for (MedicionSignos medicion : atencion.getMedicionSignos()) {
                    System.out.println("    Signos: " + medicion.getTemperatura() + " C, "
                            + medicion.getFrecuenciaCardiaca() + " lpm, presion "
                            + medicion.getPresionSistolica() + "/" + medicion.getPresionDiastolica()
                            + " mmHg, saturacion " + medicion.getSaturacionOxigeno() + "%");
                }
            }
        }
    }

    private static void enviarNotificacion() {
        System.out.println("\n--- Envio de notificacion ---");
        System.out.println("1. Paciente");
        System.out.println("2. Profesional de salud");
        int tipo = leerEntero("Seleccione el destinatario: ", 1, 2);
        String mensaje = leerTexto("Mensaje: ");

        if (tipo == 1) {
            if (pacientes.isEmpty()) {
                System.out.println("No hay pacientes registrados.");
                return;
            }
            seleccionarPaciente().notificar(mensaje);
        } else {
            if (profesionales.isEmpty()) {
                System.out.println("No hay profesionales registrados.");
                return;
            }
            seleccionarProfesional().notificar(mensaje);
        }
    }

    private static Paciente seleccionarPaciente() {
        while (true) {
            System.out.println("Pacientes disponibles:");
            for (Paciente paciente : pacientes) {
                System.out.println("  " + paciente.getIdentificacion() + " - " + paciente.getNombre());
            }
            String identificacion = leerTexto("Identificacion del paciente: ");
            for (Paciente paciente : pacientes) {
                if (paciente.getIdentificacion().equalsIgnoreCase(identificacion)) {
                    return paciente;
                }
            }
            System.out.println("No se encontro ese paciente. Intente nuevamente.");
        }
    }

    private static ProfesionalSalud seleccionarProfesional() {
        while (true) {
            System.out.println("Profesionales disponibles:");
            for (ProfesionalSalud profesional : profesionales) {
                System.out.println("  " + profesional.getIdentificacion() + " - " + profesional.getNombre());
            }
            String identificacion = leerTexto("Identificacion del profesional: ");
            for (ProfesionalSalud profesional : profesionales) {
                if (profesional.getIdentificacion().equalsIgnoreCase(identificacion)) {
                    return profesional;
                }
            }
            System.out.println("No se encontro ese profesional. Intente nuevamente.");
        }
    }

    private static ServicioDomiciliario seleccionarServicioSinAtencion() {
        List<ServicioDomiciliario> pendientes = new ArrayList<>();
        for (ServicioDomiciliario servicio : servicios) {
            if (servicio.getAtencionMedica() == null) {
                pendientes.add(servicio);
            }
        }
        if (pendientes.isEmpty()) {
            System.out.println("No hay servicios pendientes de atencion.");
            return null;
        }

        while (true) {
            System.out.println("Servicios pendientes:");
            for (ServicioDomiciliario servicio : pendientes) {
                System.out.println("  " + servicio.getCodigoUnico() + " - "
                        + servicio.getPaciente().getNombre() + " - " + servicio.getMotivo());
            }
            String codigo = leerTexto("Codigo del servicio: ");
            for (ServicioDomiciliario servicio : pendientes) {
                if (servicio.getCodigoUnico().equalsIgnoreCase(codigo)) {
                    return servicio;
                }
            }
            System.out.println("No se encontro ese servicio. Intente nuevamente.");
        }
    }

    private static String leerIdentificacionUnica() {
        while (true) {
            String identificacion = leerTexto("Identificacion: ");
            boolean existe = false;
            for (Paciente paciente : pacientes) {
                existe |= paciente.getIdentificacion().equalsIgnoreCase(identificacion);
            }
            for (ProfesionalSalud profesional : profesionales) {
                existe |= profesional.getIdentificacion().equalsIgnoreCase(identificacion);
            }
            if (!existe) {
                return identificacion;
            }
            System.out.println("Esa identificacion ya esta registrada. Ingrese una diferente.");
        }
    }

    private static String leerNumeroRegistroUnico() {
        while (true) {
            String valor = leerTexto("Numero de registro profesional: ");
            boolean existe = false;
            for (ProfesionalSalud profesional : profesionales) {
                if (profesional.getNumeroRegistroProfesional().equalsIgnoreCase(valor)) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                return valor;
            }
            System.out.println("Ese valor ya esta registrado. Ingrese uno diferente.");
        }
    }

    private static String leerCodigoEquipoUnico() {
        while (true) {
            String codigo = leerTexto("Codigo del equipo: ");
            boolean existe = false;
            for (EquipoMedico equipo : equipos) {
                if (equipo.getCodigo().equalsIgnoreCase(codigo)) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                return codigo;
            }
            System.out.println("Ese codigo ya esta registrado. Ingrese uno diferente.");
        }
    }

    private static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = entrada.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println("El valor no puede estar vacio.");
        }
    }

    private static int leerEntero(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String valor = entrada.nextLine().trim();
            try {
                int numero = Integer.parseInt(valor);
                if (numero >= minimo && numero <= maximo) {
                    return numero;
                }
            } catch (NumberFormatException ignored) {
                // Se solicita el valor de nuevo debajo.
            }
            System.out.println("Ingrese un numero entre " + minimo + " y " + maximo + ".");
        }
    }

    private static double leerDecimal(String mensaje, double minimo, double maximo) {
        while (true) {
            System.out.print(mensaje);
            String valor = entrada.nextLine().trim().replace(',', '.');
            try {
                double numero = Double.parseDouble(valor);
                if (Double.isFinite(numero) && numero >= minimo && numero <= maximo) {
                    return numero;
                }
            } catch (NumberFormatException ignored) {
                // Se solicita el valor de nuevo debajo.
            }
            System.out.println("Ingrese un numero entre " + minimo + " y " + maximo + ".");
        }
    }

    private static boolean leerSiNo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String respuesta = entrada.nextLine().trim();
            if (respuesta.equalsIgnoreCase("s")) {
                return true;
            }
            if (respuesta.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Responda s o n.");
        }
    }
}
