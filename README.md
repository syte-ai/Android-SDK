## Syte Android SDK

The Syte SDK allows you to build image detection, visual search and product recommendations into your company's applications, enabling you to personalize your app experience for your users.
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

`
    SyteConfiguration syteConfiguration = new SyteConfiguration(
        context,
        <account_id>,
        <api_signature>
    );
    syteConfiguration.setLocale("en_US);
`

Then you'll need to create a new instance of InitSyte class and start the session passing the configuration instance:


    InitSyte initSyte = InitSyte.newInstance();
    initSyte.startSessionAsync(syteConfiguration, syteResult -> {
        // Check the result here and process it.
    });

Event fires automatically: https://syteapi.com/et?name=syte_init&account_id=[account_id]&session_id=[session_id]&sig=[api_signature]&syte_uuid=[user_id]&build_num=&lang=&tags=android_sdk&syte_url_referer=[app_name]
 
API used: https://cdn.syteapi.com/accounts/[account_id]

## Image Search

Object detection ("bounds") and a similarity search ("Items") per object detected in the image. 
Search can be performed with a wild image or image URL.

# URL image search
To use the Url image Search functionality do the following:

1. Retrieve the Image Search client:

    `ImageSearchClient imageSearchClient = initSyte.getImageSearchClient();`

2. Create the UrlImageSearch class instance and pass the required data.

    `UrlImageSearch urlImageSearch = new UrlImageSearch(
        <image URL>,
        <Syte product type>
    );`

3. Retrieve bounds:


    `SyteResult<BoundsResult> result = imageSearchClient.getBounds(urlImageSearch);`

4. Retrieve offers for a bound:

    `SyteResult<ItemsResult> imageSearchClient.getItems(result.data.getBounds().get(index), null);`

You can pass CropCoordinates instance instead of *null* here to enable the crop functionality.

# "Wild" image search

To use the "Wild" image Search functionality do the following:

1. Retrieve the Image Search client:

    `ImageSearchClient imageSearchClient = initSyte.getImageSearchClient();`

2. Create the ImageSearch class instance and pass the required data.

    `ImageSearch ImageSearch = new ImageSearch(
        <image Uri>
    );`

3. Retrieve bounds:

    `SyteResult<BoundsResult> result = imageSearchClient.getBounds(context, imageSearch);`

4. Offers retrieval process is the same as for Url image search.

# Product Recommendations
To use the "Recommendations" functionality, do the following:

1. Retrieve the Recommendations client:

    `ProductRecommendationClient client = initSyte.getProductRecommendationClient();`

2. Use this client to get results for different features:

*   `SyteResult<SimilarProductsResult> getSimilarProducts(
        SimilarProducts similarProducts
    );`
*   `SyteResult<ShopTheLookResult> getShopTheLook(
        ShopTheLook shopTheLook
    );`
*   `SyteResult<PersonalizationResult> getPersonalization(
        PersonalizationRequestData personalizationRequestData
    );`
    
# Data Collection

The SDK can be used to fire various events to Syte. Example:

    `initSyte.fireEvent(new EventOfferClick());`
