package itst.ws;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import itst.model.Paciente;

@Path("/paciente")
public class PacienteService {
public static final List<Paciente> pacientes = new ArrayList<>();
	
	static {
		pacientes.add(new Paciente(1,"Luis Eduardo","Hernández","Gil",12,"Alta"));
		pacientes.add(new Paciente(2,"Victor","Hernández","Gil",13,"Internado"));
		pacientes.add(new Paciente(3,"Johana","Hernández","Gil",8,"Internado"));
	}
	
	@GET
    @Path("contar")
    @Produces(MediaType.TEXT_PLAIN)
    public int contarPacientes() {
        return pacientes.size();
    }
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Paciente obtenerPacienteById(@PathParam("id") int id) {
		Paciente respuesta = new Paciente();
		for(Paciente p:pacientes) {
			if(p.getId()==id) {
				respuesta = p;
			}
		}
		return respuesta;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Paciente> obtenerTodosPacientes() {
		return pacientes;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response crearPaciente(Paciente paciente) {
		pacientes.add(paciente);
		return Response.ok("Paciente registrado", MediaType.APPLICATION_JSON).build();
	}
	
	@PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editarPacienteById(@PathParam("id") int id, Paciente paciente) {
		boolean respuesta = false;
		for(Paciente p:pacientes) {
			if(p.getId()==id) {
				p.setNombre(paciente.getNombre());
				p.setApellidoPaterno(paciente.getApellidoPaterno());
				p.setApellidoPaterno(paciente.getApellidoMaterno());
				p.setHabitacion(paciente.getHabitacion());
				p.setStatus(paciente.getStatus());
				respuesta = true;
			}
		}
		if(respuesta == false) {
			return Response.ok("Paciente no encontrado", MediaType.APPLICATION_JSON).build();
		}else {
			return Response.ok("Paciente modificado", MediaType.APPLICATION_JSON).build();
		}
    }
	
	@DELETE
    @Path("{id}")
    public Response eliminarPacienteById(@PathParam("id") int id) {
		boolean respuesta = false;
		Paciente paciente = new Paciente();
		for(Paciente p:pacientes) {
			if(p.getId()==id) {
				paciente = p;
				respuesta = true;
			}
		}
		if(respuesta == false) {
			return Response.ok("Paciente no encontrado", MediaType.APPLICATION_JSON).build();
		}else {
			pacientes.remove(paciente);
			return Response.ok("Paciente eliminado", MediaType.APPLICATION_JSON).build();
		}
    }
	
	@HEAD
	public Response getStatus() {
		return Response.noContent().header("running", true).build();
	}
	
	@GET
	@Path("consulta/{a},{b}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Paciente> obtenerPacientesRango(@PathParam("a") int a,@PathParam("b") int b) {
		List<Paciente> pacientesD = new ArrayList<>();
		for(int i=a; i<=b; i++) {
			for(Paciente p:pacientes) {
				if(p.getId()==i) {
					pacientesD.add(p);
				}
			}
		}
		return pacientesD;	
	}
}
