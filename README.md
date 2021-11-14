## Syte Android SDK

The Syte SDK allows you to build image detection, visual search, product recommendations and augmented search into your company's applications, enabling you to personalize your app experience for your users.
This guide contains information about how to install and use the SDK for Android Apps.

Note: This SDK requires Android API 21 or higher.

## Set up

1. Add the JitPack repository to your build file

	`allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}`
	
2. Add the SDK dependency

    `dependencies {
	        implementation 'com.github.syte-ai:Android-SDK:<version>'
	}`
	
For example:

    dependencies {
        implementation 'com.github.syte-ai:Android-SDK:1.0.0'
	}
	
3. Sync your project
4. In case the **minifyEnabled** is set to true in your build.gradle,
add the following rule to your proguard-rules.pro file:

`-keep class com.syte.ai.android_sdk.data.** { *; }`

## Account Credentials

In order to use this SDK, please contact Syte for your account ID and signature.     

## Use the SDK

# Initialization

To start using the Syte SDK, instantiate the SyteConfiguration object and pass your API credentials to its constructor. 
Your credentials can be found in Syte’s Platform - Settings - API Keys.
Then use the created instance to set the locale.

    SyteConfiguration syteConfiguration = new SyteConfiguration(
        context,
        <account_id>,
        <api_signature>
    );
    syteConfiguration.setLocale("en_US");

Then you'll need to initialize Syte class passing the configuration instance:

    Syte syte = Syte.newInstance(syteConfiguration);

API used: https://cdn.syteapi.com/accounts/[account_id]

To retrieve the instance of SytePlatformSettings use the syte.getPlatformSettings() method.

## Image Search

Object detection ("bounds") and a similarity search ("Items") per object detected in the image. 
Search can be performed with an image or image URL.

To use the image Search functionality do the following:

1. Create dedicated class instance and pass the required data.

For Url image search:

    `UrlImageSearch urlImageSearch = new UrlImageSearch(
        <image URL>,
        <Syte product type>
    );`
    
For image search:
    
    `ImageSearch ImageSearch = new ImageSearch(
        <image Uri>
    );`

2. Retrieve bounds:

For Url image search:

    `SyteResult<BoundsResult> result = syte.getBoundsForImageUrl(urlImageSearch);`
    
For image search:

    `SyteResult<BoundsResult> result = syte.getBoundsForImage(context, imageSearch);`

3. Retrieve Items for a bound:

    `SyteResult<ItemsResult> syte.getItemsForBound(result.data.getBounds().get(index), null);`

You can pass CropCoordinates instance instead of *null* here to enable the crop functionality. Example:

    CropCoordinates coordinates = new CropCoordinates(0.2, 0.2, 0.8, 0.8);
    // The coordinates should be relative ranging from 0.0 to 1.0
        // CropCoordinates class reflects two points of the image: top left and bottom right.
    SyteResult<ItemsResult> syte.getItems(result.data.getBounds().get(index), coordinates);

**NOTE**
Items for the first bound will be retrieved by default.
Use **urlImageSearch.setRetrieveItemsForTheFirstBound(false)**  or **imageSearch.setRetrieveItemsForTheFirstBound(false)** to disable this behaviour.
To get the items for the first bound use the BoundsResult.getItemsForFirstBound() method.

# Product Recommendations
To use the "Recommendations" functionality, do the following:

1. Use Syte class instance to get results for different features:

*   `SyteResult<SimilarProductsResult> getSimilarProducts(
        SimilarProducts similarProducts
    );`
*   `SyteResult<ShopTheLookResult> getShopTheLook(
        ShopTheLook shopTheLook
    );`
*   `SyteResult<PersonalizationResult> getPersonalization(
        Personalization personalization
    );`
    
**NOTE:** You must add at least one product ID to use the "Personalization" functionality. To do this use the **Syte.addViewedProduct(String)** method.

# Personalized ranking

Enabling the personalized ranking will attach the list of viewed products to the requests. 
To add a product to the list of viewed ones use the **Syte.addViewedProduct(String)** method.
To enable this functionality use the **setPersonalizedRanking(true)** method. 
It is supported in the following classes: **UrlImageSearch, ImageSearch, ShopTheLook, SimilarProducts**.
Personalized ranking is disabled by default.

# Data Collection

The SDK can be used to fire various events to Syte. Example:

    syte.fireEvent(new EventCheckoutStart());

# Text Search

The SDK can be used for the Text Search functionality.

There are 3 main features:

1. Popular Searches. Will retrieve the list of the most popular searches.

    `syte.getPopularSearches("en_US");`

2. Text search. Will retrieve the results for the specified query.

    `syte.getTextSearch(TextSearch textSearch);`

    To retrieve a list of recent text searches use syte.getRecentTextSearches() method.

3. Auto-complete. Text auto-completion functionality.

    `syte.getAutoCompleteForTextSearch("query", "en_US", syteResult -> {`
        `// Process the results here.`
    `});`
