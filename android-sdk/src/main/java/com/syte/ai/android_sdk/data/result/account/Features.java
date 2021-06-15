package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Features{

	@SerializedName("collaborativeFiltering")
	private CollaborativeFiltering collaborativeFiltering;

	@SerializedName("sessionBased")
	private SessionBased sessionBased;

	@SerializedName("associationRules")
	private AssociationRules associationRules;

	@SerializedName("cameraHandler")
	private CameraHandler cameraHandler;

	@SerializedName("boundingBox")
	private BoundingBox boundingBox;

	@SerializedName("historyRouter")
	private HistoryRouter historyRouter;

	@SerializedName("shopTheLook")
	private ShopTheLook shopTheLook;

	@SerializedName("similarItems")
	private SimilarItems similarItems;

	@SerializedName("relatedItems")
	private RelatedItems relatedItems;

	@SerializedName("recommendations")
	private Recommendations recommendations;

	@SerializedName("getSkuConfig")
	private GetSkuConfig getSkuConfig;

	@SerializedName("productCard")
	private ProductCard productCard;

	@SerializedName("personalization")
	private Personalization personalization;

	@SerializedName("currency")
	private Currency currency;

	@SerializedName("discoveryButton")
	private DiscoveryButton discoveryButton;

	public CollaborativeFiltering getCollaborativeFiltering(){
		return collaborativeFiltering;
	}

	public SessionBased getSessionBased(){
		return sessionBased;
	}

	public AssociationRules getAssociationRules(){
		return associationRules;
	}

	public CameraHandler getCameraHandler(){
		return cameraHandler;
	}

	public BoundingBox getBoundingBox(){
		return boundingBox;
	}

	public HistoryRouter getHistoryRouter(){
		return historyRouter;
	}

	public ShopTheLook getShopTheLook(){
		return shopTheLook;
	}

	public SimilarItems getSimilarItems(){
		return similarItems;
	}

	public RelatedItems getRelatedItems(){
		return relatedItems;
	}

	public Recommendations getRecommendations(){
		return recommendations;
	}

	public GetSkuConfig getGetSkuConfig(){
		return getSkuConfig;
	}

	public ProductCard getProductCard(){
		return productCard;
	}

	public Personalization getPersonalization(){
		return personalization;
	}

	public Currency getCurrency(){
		return currency;
	}

	public DiscoveryButton getDiscoveryButton(){
		return discoveryButton;
	}

	@Override
 	public String toString(){
		return 
			"Features{" + 
			"collaborativeFiltering = '" + collaborativeFiltering + '\'' + 
			",sessionBased = '" + sessionBased + '\'' + 
			",associationRules = '" + associationRules + '\'' + 
			",cameraHandler = '" + cameraHandler + '\'' + 
			",boundingBox = '" + boundingBox + '\'' + 
			",historyRouter = '" + historyRouter + '\'' + 
			",shopTheLook = '" + shopTheLook + '\'' + 
			",similarItems = '" + similarItems + '\'' + 
			",relatedItems = '" + relatedItems + '\'' + 
			",recommendations = '" + recommendations + '\'' + 
			",getSkuConfig = '" + getSkuConfig + '\'' + 
			",productCard = '" + productCard + '\'' + 
			",personalization = '" + personalization + '\'' +
			",currency = '" + currency + '\'' + 
			",discoveryButton = '" + discoveryButton + '\'' + 
			"}";
		}
}