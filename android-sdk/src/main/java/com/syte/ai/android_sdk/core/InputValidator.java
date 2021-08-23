package com.syte.ai.android_sdk.core;

import android.content.Context;

import com.syte.ai.android_sdk.data.ImageSearch;
import com.syte.ai.android_sdk.data.Personalization;
import com.syte.ai.android_sdk.data.ShopTheLook;
import com.syte.ai.android_sdk.data.SimilarProducts;
import com.syte.ai.android_sdk.data.TextSearch;
import com.syte.ai.android_sdk.data.UrlImageSearch;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

class InputValidator {

    public static void validateInput(ImageSearch requestData) throws SyteWrongInputException {
        if (requestData == null) {
            throw new SyteWrongInputException("Request data can not be null.");
        }
        if (requestData.getImageUri() == null) {
            throw new SyteWrongInputException("Image URI can not be null.");
        }
    }

    public static void validateInput(UrlImageSearch requestData) throws SyteWrongInputException {
        if (requestData == null) {
            throw new SyteWrongInputException("Request data can not be null.");
        }
        if (requestData.getImageUrl() == null) {
            throw new SyteWrongInputException("Image URL can not be null.");
        }
        if (requestData.getProductType() == null) {
            throw new SyteWrongInputException("Product type can not be null.");
        }
    }

    public static void validateInput(Bound bound) throws SyteWrongInputException {
        if (bound == null) {
            throw new SyteWrongInputException("Bound can not be null");
        }
    }

    public static void validateInput(Context context) throws SyteWrongInputException {
        if (context == null) {
            throw new SyteWrongInputException("Context can not be null.");
        }
    }

    public static void validateInput(SyteConfiguration configuration) throws SyteWrongInputException {
        if (configuration == null) {
            throw new SyteWrongInputException("Configuration can not be null.");
        }
        if (configuration.getAccountId() == null) {
            throw new SyteWrongInputException("Account ID can not be null.");
        }
        if(configuration.getApiSignature() == null) {
            throw new SyteWrongInputException("Signature can not be null.");
        }
    }

    public static void validateInput(SimilarProducts requestData) throws SyteWrongInputException {
        if (requestData == null) {
            throw new SyteWrongInputException("Request data can not be null.");
        }
        if (requestData.getSku() == null) {
            throw new SyteWrongInputException("SKU can not be null.");
        }
        if (requestData.getImageUrl() == null) {
            throw new SyteWrongInputException("Image URL can not be null");
        }
    }

    public static void validateInput(ShopTheLook requestData) throws SyteWrongInputException {
        if (requestData == null) {
            throw new SyteWrongInputException("Request data can not be null.");
        }
        if (requestData.getSku() == null) {
            throw new SyteWrongInputException("SKU can not be null.");
        }
        if (requestData.getImageUrl() == null) {
            throw new SyteWrongInputException("Image URL can not be null");
        }
    }

    public static void validateInput(Personalization requestData) throws SyteWrongInputException {
        if (requestData == null) {
            throw new SyteWrongInputException("Request data can not be null.");
        }
    }

    public static void validateInput(String string) throws SyteWrongInputException {
        if (string == null || string.isEmpty()) {
            throw new SyteWrongInputException("Viewed product can not be empty.");
        }
    }

    public static void validateInput(TextSearch textSearch) throws SyteWrongInputException {
        if (textSearch == null) {
            throw new SyteWrongInputException("Request data can not be null.");
        }
        if (textSearch.getQuery() == null) {
            throw new SyteWrongInputException("Query can not be null.");
        }
    }

}
