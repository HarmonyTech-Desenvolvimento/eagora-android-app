package harmonytech.praagoraa.controller.util;

import java.util.ArrayList;
import java.util.HashMap;

public class Singleton {

    private static Singleton uniqueInstance = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return uniqueInstance;
    }

    public HashMap<String, HashMap<String, ArrayList<String>>> getSegmentos(){

        HashMap<String, HashMap<String, ArrayList<String>>> subcategoriasSegmentos = new HashMap<>();

        HashMap<String, ArrayList<String>> assistenciaTelas = new HashMap<>();

        ArrayList<String> assistenciaTecnica = new ArrayList<>();
        assistenciaTecnica.add("Ar condicionado");
        assistenciaTecnica.add("Câmera");
        assistenciaTecnica.add("Computador");
        assistenciaTecnica.add("Eletrodomésticos");
        assistenciaTecnica.add("Eletrônicos");
        assistenciaTecnica.add("Tablets");
        assistenciaTecnica.add("Telefonia");
        assistenciaTecnica.add("Outros");

        ArrayList<String> assistenciaTecnicaFirebase = new ArrayList<>();
        assistenciaTecnicaFirebase.add("ar_condicionado");
        assistenciaTecnicaFirebase.add("camera");
        assistenciaTecnicaFirebase.add("computador");
        assistenciaTecnicaFirebase.add("eletrodomesticos");
        assistenciaTecnicaFirebase.add("eletronicos");
        assistenciaTecnicaFirebase.add("outros");
        assistenciaTecnicaFirebase.add("tablets");
        assistenciaTecnicaFirebase.add("telefonia");

        assistenciaTelas.put(Utility.HASH_MAP_TELA, assistenciaTecnica);
        assistenciaTelas.put(Utility.HASH_MAP_FIREBASE, assistenciaTecnicaFirebase);

        subcategoriasSegmentos.put(Utility.SEGMENTO_ASSISTENCIA_TECNICA, assistenciaTelas);

        HashMap<String, ArrayList<String>> animaisTelas = new HashMap<>();

        ArrayList<String> animais = new ArrayList<>();
        animais.add("Acessórios");
        animais.add("Adestrador de animais");
        animais.add("Banho e tosa");
        animais.add("Cuidador de animais");
        animais.add("Passeio para animais");
        animais.add("Vendedor de Ração");
        animais.add("Veterinário");
        animais.add("Outros");


        ArrayList<String> animaisFirebase = new ArrayList<>();
        animaisFirebase.add("acessorios");
        animaisFirebase.add("adestrador_de_animais");
        animaisFirebase.add("banho_e_tosa");
        animaisFirebase.add("cuidador_de_animais");
        animaisFirebase.add("passeio_para_animais");
        animaisFirebase.add("vendedor_de_ração");
        animaisFirebase.add("veterinário");
        animaisFirebase.add("outros");


        animaisTelas.put(Utility.HASH_MAP_TELA, animais);
        animaisTelas.put(Utility.HASH_MAP_FIREBASE, animaisFirebase);

        subcategoriasSegmentos.put(Utility.SEGMENTO_ANIMAIS, animaisTelas);

        HashMap<String, ArrayList<String>> arteCulturaTelas = new HashMap<>();

        ArrayList<String> arteCultura = new ArrayList<>();
        arteCultura.add("Aluguel de teatro");
        arteCultura.add("Artesão");
        arteCultura.add("Companhia de dança");
        arteCultura.add("Compahia de teatro");
        arteCultura.add("Escultor");
        arteCultura.add("Desenhista");
        arteCultura.add("Pintor de Telas");
        arteCultura.add("Roteirista");
        arteCultura.add("Cantor");
        arteCultura.add("Músico");
        arteCultura.add("Outros");



        ArrayList<String> arteCulturaFirebase = new ArrayList<>();
        arteCulturaFirebase.add("aluguel_de_teatro");
        arteCulturaFirebase.add("artesão");
        arteCulturaFirebase.add("companhia_de_danca");
        arteCulturaFirebase.add("compahia_de_teatro");
        arteCulturaFirebase.add("escultor");
        arteCulturaFirebase.add("desenhista");
        arteCulturaFirebase.add("pintor_de_telas");
        arteCulturaFirebase.add("roteirista");
        arteCulturaFirebase.add("cantor");
        arteCulturaFirebase.add("musico");
        arteCulturaFirebase.add("outros");

        arteCulturaTelas.put(Utility.HASH_MAP_TELA, arteCultura);
        arteCulturaTelas.put(Utility.HASH_MAP_FIREBASE, arteCulturaFirebase);

        subcategoriasSegmentos.put(Utility.SEGMENTO_ARTE_CULTURA, arteCulturaTelas);

        HashMap<String, ArrayList<String>> aulasTelas = new HashMap<>();

        ArrayList<String> aulas = new ArrayList<>();
        aulas.add("Artes e artesanato");
        aulas.add("Concursos");
        aulas.add("Aulas de dança");
        aulas.add("Aulas particulares");
        aulas.add("Esportes");
        aulas.add("Aulas de idiomas");
        aulas.add("Aulas de informática");
        aulas.add("Aulas de lutas");
        aulas.add("Aulas de música");
        aulas.add("Outros");

        ArrayList<String> aulasFirebase = new ArrayList<>();
        aulasFirebase.add("artes_e_artesanato");
        aulasFirebase.add("concursos");
        aulasFirebase.add("aulas_de_danca");
        aulasFirebase.add("aulas_particulares");
        aulasFirebase.add("esportes");
        aulasFirebase.add("aulas_de_idiomas");
        aulasFirebase.add("aulas_de_informatica");
        aulasFirebase.add("aulas_de_lutas");
        aulasFirebase.add("aulas_de_musica");
        aulasFirebase.add("outros");

        aulasTelas.put(Utility.HASH_MAP_TELA, aulas);
        aulasTelas.put(Utility.HASH_MAP_FIREBASE, aulasFirebase);

        subcategoriasSegmentos.put(Utility.SEGMENTO_AULAS, aulasTelas);

        HashMap<String, ArrayList<String>> autosTelas = new HashMap<>();

        ArrayList<String> autos = new ArrayList<>();
        autos.add("Alarme automotivo");
        autos.add("Arcondicionado");
        autos.add("Funilaria");
        autos.add("Inspeção veicular");
        autos.add("Insulfilm");
        autos.add("Martelinho de ouro");
        autos.add("Revisão");
        autos.add("Som automotivo");
        autos.add("Outros");

        ArrayList<String> autosFirebase = new ArrayList<>();
        autosFirebase.add("alarme_automotivo");
        autosFirebase.add("arcondicionado");
        autosFirebase.add("funilaria");
        autosFirebase.add("inspecao veicular");
        autosFirebase.add("insulfilm");
        autosFirebase.add("martelinho_de_ouro");
        autosFirebase.add("revisao");
        autosFirebase.add("som_automotivo");
        autos.add("outros");

        autosTelas.put(Utility.HASH_MAP_TELA, autos);
        autosTelas.put(Utility.HASH_MAP_FIREBASE, autosFirebase);

        subcategoriasSegmentos.put(Utility.SEGMENTO_AUTOS, autosTelas);

        HashMap<String, ArrayList<String>> belezaEsteticaTelas = new HashMap<>();

        ArrayList<String> belezaEstetica = new ArrayList<>();
        belezaEstetica.add("Cabeleireiros");
        belezaEstetica.add("Corte e costura");
        belezaEstetica.add("Depilação");
        belezaEstetica.add("Design de sobrancelhas");
        belezaEstetica.add("Esteticista");
        belezaEstetica.add("Manicure e pedicure");
        belezaEstetica.add("Maquiadores");
        belezaEstetica.add("Personal stylist");
        belezaEstetica.add("Sapateiro");
        belezaEstetica.add("Outros");

        ArrayList<String> belezaEsteticaFirebase = new ArrayList<>();
        belezaEsteticaFirebase.add("cabeleireiros");
        belezaEsteticaFirebase.add("corte_e_costura");
        belezaEsteticaFirebase.add("depilacao");
        belezaEsteticaFirebase.add("design_de_sobrancelhas");
        belezaEsteticaFirebase.add("esteticista");
        belezaEsteticaFirebase.add("manicure_e_pedicure");
        belezaEsteticaFirebase.add("maquiadores");
        belezaEsteticaFirebase.add("personal_stylist");
        belezaEsteticaFirebase.add("sapateiro");
        belezaEsteticaFirebase.add("outros");

        belezaEsteticaTelas.put(Utility.HASH_MAP_TELA, belezaEstetica);
        belezaEsteticaTelas.put(Utility.HASH_MAP_FIREBASE, belezaEsteticaFirebase);

        subcategoriasSegmentos.put(Utility.SEGMENTO_BELEZA_ESTETICA, belezaEsteticaTelas);

        HashMap<String, ArrayList<String>> construcaoReformasTelas = new HashMap<>();

        ArrayList<String> construcaoReformas = new ArrayList<>();
        construcaoReformas.add("Arquiteto");
        construcaoReformas.add("Automação Residencial");
        construcaoReformas.add("Chaveiro");
        construcaoReformas.add("Decorador");
        construcaoReformas.add("Dedetizador");
        construcaoReformas.add("Desentupidor");
        construcaoReformas.add("Eletricista");
        construcaoReformas.add("Encanador");
        construcaoReformas.add("Engenheiro");
        construcaoReformas.add("Gesso e Drywall");
        construcaoReformas.add("Impermeabilizador");
        construcaoReformas.add("Jardineiro");
        construcaoReformas.add("Marceneiro");
        construcaoReformas.add("Montador de móveis");
        construcaoReformas.add("Paisagista");
        construcaoReformas.add("Pedreiro");
        construcaoReformas.add("Pintor");
        construcaoReformas.add("Segurança Eletrônica");
        construcaoReformas.add("Serraria");
        construcaoReformas.add("Vidraceiro");
        construcaoReformas.add("Outros");


        ArrayList<String> construcaoReformasFirebase = new ArrayList<>();
        construcaoReformasFirebase.add("arquiteto");
        construcaoReformasFirebase.add("automação_esidencial");
        construcaoReformasFirebase.add("chaveiro");
        construcaoReformasFirebase.add("decorador");
        construcaoReformasFirebase.add("dedetizador");
        construcaoReformasFirebase.add("desentupidor");
        construcaoReformasFirebase.add("eletricista");
        construcaoReformasFirebase.add("encanador");
        construcaoReformasFirebase.add("engenheiro");
        construcaoReformasFirebase.add("gesso_e_drywall");
        construcaoReformasFirebase.add("impermeabilizador");
        construcaoReformasFirebase.add("jardineiro");
        construcaoReformasFirebase.add("marceneiro");
        construcaoReformasFirebase.add("montador_de_moveis");
        construcaoReformasFirebase.add("paisagista");
        construcaoReformasFirebase.add("pedreiro");
        construcaoReformasFirebase.add("pintor");
        construcaoReformasFirebase.add("segurança_eletronica");
        construcaoReformasFirebase.add("serraria");
        construcaoReformasFirebase.add("vidraceiro");
        construcaoReformasFirebase.add("outros");


        construcaoReformasTelas.put(Utility.HASH_MAP_TELA, construcaoReformas);
        construcaoReformasTelas.put(Utility.HASH_MAP_FIREBASE, construcaoReformasFirebase);

        subcategoriasSegmentos.put(Utility.SEGMENTO_CONSTRUCAO, construcaoReformasTelas);

        HashMap<String, ArrayList<String>> consultoriaTelas = new HashMap<>();

        ArrayList<String> consultoria = new ArrayList<>();
        consultoria.add("Advogados");
        consultoria.add("Acessoria de imprensa");
        consultoria.add("Auxilio administrativo");
        consultoria.add("Consultor pessoal");
        consultoria.add("Consultoria especializada");
        consultoria.add("Contador");
        consultoria.add("Detetive particular");
        consultoria.add("Digitalizar docmentos");
        consultoria.add("Economia e finanças");
        consultoria.add("Segurança do trabalho");
        consultoria.add("Tradutores");
        consultoria.add("Outros");

        ArrayList<String> consultoriaFirebase = new ArrayList<>();
        consultoriaFirebase.add("advogados");
        consultoriaFirebase.add("acessoria_de_mprensa");
        consultoriaFirebase.add("auxilio_administrativo");
        consultoriaFirebase.add("consultor_pessoal");
        consultoriaFirebase.add("consultoria_especializada");
        consultoriaFirebase.add("contador");
        consultoriaFirebase.add("detetive_particular");
        consultoriaFirebase.add("digitalizar_docmentos");
        consultoriaFirebase.add("economia_e_finanças");
        consultoriaFirebase.add("segurança_do_trabalho");
        consultoriaFirebase.add("tradutores");
        consultoriaFirebase.add("outros");


        consultoriaTelas.put(Utility.HASH_MAP_TELA, consultoria);
        consultoriaTelas.put(Utility.HASH_MAP_FIREBASE, consultoriaFirebase);

        subcategoriasSegmentos.put(Utility.SEGMENTO_CONSULTORIA, consultoriaTelas);

        HashMap<String, ArrayList<String>> designTecnologiaTelas = new HashMap<>();

        ArrayList<String> designTecnologia = new ArrayList<>();
        designTecnologia.add("Animação");
        designTecnologia.add("Apps para smartphone");
        designTecnologia.add("Áudio e vídeo");
        designTecnologia.add("Convites");
        designTecnologia.add("Criação de logos");
        designTecnologia.add("Desenvolvimento de sites");
        designTecnologia.add("Diagramador");
        designTecnologia.add("Edição de fotos");
        designTecnologia.add("Ilustração");
        designTecnologia.add("Marketing online");
        designTecnologia.add("Web Desing");
        designTecnologia.add("Outros");


        ArrayList<String> designTecnologiaFirebase = new ArrayList<>();
        designTecnologiaFirebase.add("animação");
        designTecnologiaFirebase.add("apps_para_smartphone");
        designTecnologiaFirebase.add("audio_e_video");
        designTecnologiaFirebase.add("convites");
        designTecnologiaFirebase.add("criacao_de_logos");
        designTecnologiaFirebase.add("desenvolvimento_de_sites");
        designTecnologiaFirebase.add("diagramador");
        designTecnologiaFirebase.add("edição_de_fotos");
        designTecnologiaFirebase.add("ilustração");
        designTecnologiaFirebase.add("marketing_online");
        designTecnologiaFirebase.add("web_Desing");
        designTecnologiaFirebase.add("outros");


        designTecnologiaTelas.put(Utility.HASH_MAP_TELA, designTecnologia);
        designTecnologiaTelas.put(Utility.HASH_MAP_FIREBASE, designTecnologiaFirebase);

        subcategoriasSegmentos.put(Utility.SEGMENTO_DESIGN, designTecnologiaTelas);

        HashMap<String, ArrayList<String>> eventosTelas = new HashMap<>();

        ArrayList<String> eventos = new ArrayList<>();
        eventos.add("Animação de festas");
        eventos.add("Acessor de eventos");
        eventos.add("Bandas e cantores");
        eventos.add("Batenders");
        eventos.add("Brindes");
        eventos.add("Buffet completo");
        eventos.add("Churrasqueiro");
        eventos.add("Confeitaria");
        eventos.add("Decoração");
        eventos.add("DJs");
        eventos.add("Equipamentos para festa");
        eventos.add("Fotografia");
        eventos.add("Garçons e copeiros");
        eventos.add("Gravação de vídeos");
        eventos.add("Recepcionistas");
        eventos.add("Segurança");
        eventos.add("Outros");


        ArrayList<String> eventosTelasFirebase = new ArrayList<>();
        eventosTelasFirebase.add("animacao_de_festas");
        eventosTelasFirebase.add("acessor_de_eventos");
        eventosTelasFirebase.add("bandas_e_cantores");
        eventosTelasFirebase.add("batenders");
        eventosTelasFirebase.add("brindes");
        eventosTelasFirebase.add("buffet_completo");
        eventosTelasFirebase.add("churrasqueiro");
        eventosTelasFirebase.add("confeitaria");
        eventosTelasFirebase.add("decoracao");
        eventosTelasFirebase.add("djs");
        eventosTelasFirebase.add("equipamentos_para_festa");
        eventosTelasFirebase.add("fotografia");
        eventosTelasFirebase.add("garcons_e_copeiros");
        eventosTelasFirebase.add("gravacao_de_videos");
        eventosTelasFirebase.add("recepcionistas");
        eventosTelasFirebase.add("seguranca");
        eventosTelasFirebase.add("outros");


        eventosTelas.put(Utility.HASH_MAP_TELA, eventos);
        eventosTelas.put(Utility.HASH_MAP_FIREBASE, eventosTelasFirebase);

        subcategoriasSegmentos.put(Utility.SEGMENTO_EVENTOS, eventosTelas);

        HashMap<String, ArrayList<String>> saudeTelas = new HashMap<>();

        ArrayList<String> saude = new ArrayList<>();
        saude.add("Acompanhante de idosos");
        saude.add("Enfermeiro");
        saude.add("Fisioterapeuta");
        saude.add("Fonoaudiólogo");
        saude.add("Nutricionista");
        saude.add("Psicólogo");
        saude.add("Quiroprático");
        saude.add("Outros");


        ArrayList<String> saudeFirebase = new ArrayList<>();
        saudeFirebase.add("acompanhante_de_idosos");
        saudeFirebase.add("enfermeiro");
        saudeFirebase.add("fisioterapeuta");
        saudeFirebase.add("fonoaudiologo");
        saudeFirebase.add("nutricionista");
        saudeFirebase.add("psicologo");
        saudeFirebase.add("quiropratico");
        saudeFirebase.add("outros");


        saudeTelas.put(Utility.HASH_MAP_TELA, saude);
        saudeTelas.put(Utility.HASH_MAP_FIREBASE, saudeFirebase);

        subcategoriasSegmentos.put(Utility.SEGMENTO_SAUDE, saudeTelas);

        HashMap<String, ArrayList<String>> servicosDomesticosTelas = new HashMap<>();

        ArrayList<String> servicosDomesticos = new ArrayList<>();
        servicosDomesticos.add("Adestrador de cães");
        servicosDomesticos.add("Babá");
        servicosDomesticos.add("Cozinheira");
        servicosDomesticos.add("Diarista");
        servicosDomesticos.add("Limpeza de piscina");
        servicosDomesticos.add("Motorista");
        servicosDomesticos.add("Passadeira");
        servicosDomesticos.add("Passeador de cães");
        servicosDomesticos.add("Outros");


        ArrayList<String> servicosDomesticosFirebase = new ArrayList<>();
        servicosDomesticosFirebase.add("adestrador_de_caes");
        servicosDomesticosFirebase.add("baba");
        servicosDomesticosFirebase.add("cozinheira");
        servicosDomesticosFirebase.add("diarista");
        servicosDomesticosFirebase.add("limpeza_de_piscina");
        servicosDomesticosFirebase.add("motorista");
        servicosDomesticosFirebase.add("passadeira");
        servicosDomesticosFirebase.add("passeador_de_caes");
        servicosDomesticosFirebase.add("outros");


        servicosDomesticosTelas.put(Utility.HASH_MAP_TELA, servicosDomesticos);
        servicosDomesticosTelas.put(Utility.HASH_MAP_FIREBASE, servicosDomesticosFirebase);

        subcategoriasSegmentos.put(Utility.SEGMENTO_SERVICOS_DOMESTICOS, servicosDomesticosTelas);

        return subcategoriasSegmentos;
    }

    public ArrayList<String> getAreas(){

        ArrayList<String> areas = new ArrayList<>();

        areas.add(Utility.SEGMENTO_ANIMAIS);
        areas.add(Utility.SEGMENTO_ARTE_CULTURA);
        areas.add(Utility.SEGMENTO_ASSISTENCIA_TECNICA);
        areas.add(Utility.SEGMENTO_AULAS);
        areas.add(Utility.SEGMENTO_AUTOS);
        areas.add(Utility.SEGMENTO_BELEZA_ESTETICA);
        areas.add(Utility.SEGMENTO_CONSTRUCAO);
        areas.add(Utility.SEGMENTO_CONSULTORIA);
        areas.add(Utility.SEGMENTO_DESIGN);
        areas.add(Utility.SEGMENTO_EVENTOS);
        areas.add(Utility.SEGMENTO_SAUDE);
        areas.add(Utility.SEGMENTO_SERVICOS_DOMESTICOS);

        return areas;
    }

    public HashMap<String, String> getSegmentosFirebase(){

        HashMap<String, String> segmentosFirebase = new HashMap<>();

        segmentosFirebase.put(Utility.SEGMENTO_ANIMAIS, Utility.SEGMENTO_ANIMAIS_FIREBASE);
        segmentosFirebase.put(Utility.SEGMENTO_ARTE_CULTURA, Utility.SEGMENTO_ARTE_CULTURA_FIREBASE);
        segmentosFirebase.put(Utility.SEGMENTO_ASSISTENCIA_TECNICA, Utility.SEGMENTO_ASSISTENCIA_TECNICA_FIREBASE);
        segmentosFirebase.put(Utility.SEGMENTO_AULAS, Utility.SEGMENTO_AULAS_FIREBASE);
        segmentosFirebase.put(Utility.SEGMENTO_AUTOS, Utility.SEGMENTO_AUTOS_FIREBASE);
        segmentosFirebase.put(Utility.SEGMENTO_BELEZA_ESTETICA, Utility.SEGMENTO_BELEZA_ESTETICA_FIREBASE);
        segmentosFirebase.put(Utility.SEGMENTO_CONSTRUCAO, Utility.SEGMENTO_CONSTRUCAO_FIREBASE);
        segmentosFirebase.put(Utility.SEGMENTO_CONSULTORIA, Utility.SEGMENTO_CONSULTORIA_FIREBASE);
        segmentosFirebase.put(Utility.SEGMENTO_DESIGN, Utility.SEGMENTO_DESIGN_FIREBASE);
        segmentosFirebase.put(Utility.SEGMENTO_EVENTOS, Utility.SEGMENTO_EVENTOS_FIREBASE);
        segmentosFirebase.put(Utility.SEGMENTO_SAUDE, Utility.SEGMENTO_SAUDE_FIREBASE);
        segmentosFirebase.put(Utility.SEGMENTO_SERVICOS_DOMESTICOS, Utility.SEGMENTO_SERVICOS_DOMESTICOS_FIREBASE);

        return segmentosFirebase;
    }
}