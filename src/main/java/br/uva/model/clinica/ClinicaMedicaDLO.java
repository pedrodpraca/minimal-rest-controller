package br.uva.model.clinica;

import br.uva.model.clinica.especialidades.Especialidade;
import br.uva.model.clinica.buscas.BuscaDLO;
import br.uva.model.clinica.especialidades.EspecialidadeDLO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author csiqueira
 */
@Service
public class ClinicaMedicaDLO {

	private final static int PAGE_SIZE = 10;

	private final static int RNG_TEST_NUMBER = 158;

	private final static String[] LISTA_NOMES = {"Michael", "Jackson", "Golias", "Almirante", "Nacional", "Japones", "Coreano", "Chinesa", "Arte Milenar", "Jacinto", "Leite", "Milenar", "Teste", "Bangu", "Alameda", "Caxias", "Leblon", "Rural", "Economica", "Bom Jesus", "Santo Milagre", "De Santos", "Últimas Horas", "Maria", "Jardim", "Macarena", "Contoso", "Microsoft", "Delphi", "Debian", "Levanta até Defunto", "Coisas da Vida"};

	@Autowired
	private ClinicaMedicaDAO dao;

	@Autowired
	private BuscaDLO buscaDLO;

	@Autowired
	private EspecialidadeDLO especialidadeDLO;

	private Page<ClinicaMedica> busca(String query, TipoAtendimento tipo, Integer pageNumber) {
		PageRequest req = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "nome");
		Page<ClinicaMedica> ret = null;
		String uuid = buscaDLO.criar(query);
		Collection<TipoAtendimento> tipos = new ArrayList<TipoAtendimento>();

		if (tipo != null) {
			tipos.add(tipo);
		} else {
			tipos.add(TipoAtendimento.GRATUITO);
			tipos.add(TipoAtendimento.PRIVADO);
		}

		try {
			ret = dao.busca(uuid, tipos, req);
		} catch (Exception e) {
		}

		buscaDLO.excluir(uuid);
		return ret;
	}

	public Page<ClinicaMedica> getBuscaClinica(String query, Integer pageNumber) {
		return busca(query, null, pageNumber);
	}

	public Page<ClinicaMedica> getBuscaClinica(String query, Integer pageNumber, TipoAtendimento tipo) {
		return busca(query, tipo, pageNumber);
	}

	private String nomeAleatorio() {
		return LISTA_NOMES[(int) (Math.random() * LISTA_NOMES.length)];
	}

	private Integer intAleatorio(Integer n) {
		return (int) (Math.random() * n);
	}

	private boolean bool() {
		return intAleatorio(2) == 1;
	}

	public void criarDadosTeste() {

		int max = RNG_TEST_NUMBER;

		for (int i = 0; i < max; i++) {
			ClinicaMedica cm = new ClinicaMedica();
			cm.setEndereco("Rua " + nomeAleatorio() + " " + intAleatorio(1000));
			cm.setNome("Clínica " + nomeDuploAleatorio());
			cm.setTipoAtendimento(bool() ? TipoAtendimento.GRATUITO : TipoAtendimento.PRIVADO);

			if (bool()) {
				ExameMedico em = new ExameMedico();
				String nomeExame = "Exame " + nomeAleatorio();
				em.setNome(nomeExame);
				em.setInformacoesTecnicas("Segredo do " + nomeExame);
				cm.getExames().add(em);
			}

			int nMedicos = 1 + intAleatorio(3);
			for (int j = 0; j < nMedicos; j++) {
				MedicoClinica mc = new MedicoClinica();
				mc.setNome(nomeDuploAleatorio());
				mc.setCRM(UUID.randomUUID().toString());
				mc.setTitulo(bool() ? "Dr." : "Professor Doutor");
				cm.getMedicoClinica().add(mc);
			}

			int nEspecialidades = 1 + intAleatorio(4);

			Iterable<Especialidade> findAll = especialidadeDLO.findAll();

			List<Especialidade> especialidades = new ArrayList<Especialidade>();
			findAll.forEach(especialidades::add); // JDK 8

			for (int j = 0; j < nEspecialidades; j++) {
				cm.getEspecialidades().add(especialidades.get(intAleatorio(especialidades.size())));
			}

			int nTelefones = 2 + intAleatorio(3);
			
			for(int j = 0; j < nTelefones; j++) {
				String tel = "";
				for(int k = 0; k < 10; k++) {
					tel += intAleatorio(10);
				}
				cm.getTelefones().add(tel);
			}
			
			String email = "contato@" + cm.getNome().toLowerCase().replaceAll("[^a-z]+","") + ".com";

			cm.setEmail(email);
			
			dao.save(cm);
		}

	}

	public ClinicaMedica obterClinica(Long id) {
		ClinicaMedica cm = null;
		try {
			cm = dao.findOne(id);
		} catch (Exception e) {
		}
		return cm;
	}

	private String nomeDuploAleatorio() {
		String nome1 = nomeAleatorio();
		String nome2;
		do {
			nome2 = nomeAleatorio();
		} while (nome2.equals(nome1));
		return nome1 + " " + nome2;
	}
}
