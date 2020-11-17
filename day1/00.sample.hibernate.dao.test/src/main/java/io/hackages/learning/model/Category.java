package io.hackages.learning.model;

public enum Category {
	CAT_FOOD("CAT_FOOD",1),
	DOG_FOOD("DOG_FOOD",2),
	BIRD_FOOD ("BIRD_FOOD ",3),
;

	public final String description;
	public final int indexCategory;

	Category(String description, int indexCategory){
		this.description = description;
		this.indexCategory = indexCategory;
	}
}
