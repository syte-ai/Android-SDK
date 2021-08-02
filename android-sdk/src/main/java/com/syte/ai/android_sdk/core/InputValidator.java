package com.syte.ai.android_sdk.core;

import android.content.Context;

import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.PersonalizationRequestData;
import com.syte.ai.android_sdk.data.ShopTheLookRequestData;
import com.syte.ai.android_sdk.data.SimilarProductsRequestData;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

class InputValidator {

    public static void validateInput(ImageSearchRequestData requestData) throws SyteWrongInputException {
        if (requestData == null) {
            throw new SyteWrongInputException("Request data can not be null.");
        }
        if (requestData.getImageUri() == null) {
            throw new SyteWrongInputException("Image URI can not be null.");
        }
    }

    public static void validateInput(UrlImageSearchRequestData requestData) throws SyteWrongInputException {
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
        if(configuration.getSignature() == null) {
            throw new SyteWrongInputException("Signature can not be null.");
        }
    }

    public static void validateInput(SimilarProductsRequestData requestData) throws SyteWrongInputException {
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

    public static void validateInput(ShopTheLookRequestData requestData) throws SyteWrongInputException {
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

    public static void validateInput(PersonalizationRequestData requestData) throws SyteWrongInputException {
        if (requestData == null) {
            throw new SyteWrongInputException("Request data can not be null.");
        }
    }

}
