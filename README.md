## Introduction

Android app that searches for recipes, saves favorite links, and sends
ingredients to a shopping list.

## Overview

The app contains the following Activities:
 * A shopping list
 * A search-query-creating activity
 * A display of search results

## Getting started / Usage

The files for this project were built using Android Studio, so you will likely have the easiest
time duplicating the original behavior using the same.

1. Obtain an API key from [Spoonacular](https://spoonacular.com/food-api/console#Dashboard)
1. Obtain (or perhaps re-use) an application ID from [AdMob](https://apps.admob.com/v2/home) > _Apps_ > _Ad Units_ > _Add Ad Unit_
1. Clone this repository
   * `git clone https://github.com/munifrog/Recipe-Queue.git`
1. Open the _RecipeQ_ directory using Android Studio
1. Copy _my.keys.sample_ to _my.keys_ (in the same directory)
1. Edit _my.keys_ with values for `api_spoonacular` and `ad_mobs_application_release` as respectively obtained earlier
   * Note that a release build would also require a definition for `ad_mobs_interstitial_release`
1. Save _my.keys_
1. Setup [Firebase](https://console.firebase.google.com/u/0/) to recognize this application
   * Add Project with the name you desire
   * Add an App by clicking the Android icon
     * Define the package name (_com.example.recipe\_q_ is the commit default)
     * Choose a nickname
     * Register the App
   * Place the generated _google-services.json_ file within the _app_ folder
1. Launch the application on an emulator or a physical device
1. (Optional) Send a recipe to your application
   * Determine the Firebase Console Instance ID for the application
     * e.g., Set up a thread in the MainActivity `onCreate()` method to retrieve and log it
   ```
   new Thread(new Runnable() {
       @Override
       public void run() {
           Log.v("MainActivity", "ID: \"" + FirebaseInstanceId.getInstance().getId() + "\"");
       }
   }).start();
   ```
   * Using the Spoonacular API (and the API key you obtained earlier), find the `id`, `title`, and `image` URL for any recipe
     * The application expects the image URL in the form `https://spoonacular.com/recipeImages/{recipe_id}-312x231.jpg`
   * Within the project you created using [Firebase Console](https://console.firebase.google.com/u/0/) click on _Cloud Messaging_
   * Send a recipe with key-value pairs for `reference` (id), `title`, and `image`

## License

This started as a capstone project for the [Udacity Android Developer Nanodegree Course](https://www.udacity.com/course/android-developer-nanodegree-by-google--nd801)
and I own the rights for the project idea, although there are alternatives out there.

While I personally prefer indirect sources for finding solutions to puzzles, you may learn from
what I have done, if that helps. Just be careful to avoid plagiarism if you are also taking this
course and looking for answers.

If, on the other hand, you want to build on what I have done, that is also welcome.

