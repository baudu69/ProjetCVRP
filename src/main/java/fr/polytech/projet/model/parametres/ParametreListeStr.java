package fr.polytech.projet.model.parametres;

import java.util.List;

public class ParametreListeStr extends Parametre {
	private final List<String> stringList;
	private String value;


	public ParametreListeStr(String nom, List<String> stringList, String value) {
		super(nom);
		this.stringList = stringList;
		this.value = value;
	}

	public List<String> getStringList() {
		return stringList;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = stringList
				.stream()
				.filter(s -> s.equals(value))
				.findFirst()
				.orElseThrow();
	}
}
