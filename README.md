# Writey AI

Introducing Writey AI: Turbocharge Your Social Media Content Creation

Are you tired of spending hours crafting engaging social media posts and content? Look no further! Writey AI is here to revolutionize your social media presence. Our cutting-edge platform leverages advanced technology and a seamless user experience to help you grow your online presence quickly and effortlessly.


## The Power of GPT 3.5 Turbo API

Writey AI utilizes the formidable GPT 3.5 turbo API model to generate high-quality, personalized content for your social media channels. Say goodbye to writer's block and hello to endless inspiration. Our AI model ensures your content is not only engaging but also uniquely tailored to your brand.

## Implement

```java
public class ChatGpt {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client;
    final String model = "gpt-3.5-turbo";

    public ChatGpt() {
        client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public void callApi(String question, String data, final ApiCallback callback) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", model);

            JSONArray messageArr = new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put("role", "user");
            obj.put("content", question);
            messageArr.put(obj);

            jsonBody.put("messages", messageArr);
            jsonBody.put("max_tokens",1000);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + data)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure("Failed to load response due to " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");
                        callback.onSuccess(result.trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    callback.onFailure("Failed to load response due to " + response.body().toString());
                }
            }
        });
    }

    public interface ApiCallback {
        void onSuccess(String response);

        void onFailure(String error);
    }
}
```
## Usage

```java
void callAPI(String question, String data ) {

        ChatGpt chatGptApi = new ChatGpt();

        chatGptApi.callApi(question,data, new ChatGpt.ApiCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                });
            }
        });
    }

```
## Seamless User Authentication with Firebase

User authentication has never been easier. Writey AI simplifies the process by using Firebase real-time databases. No need for email or phone numbers. We authenticate users based on their device UUID, making it hassle-free and secure. Your user data is safe, and their coin count remains intact even if they reinstall or close the app.

## Implement

```java
DatabaseReference db;

public void FirebaseAuthentication() {
        db = FirebaseDatabase.getInstance().getReference();

        String uniquePseudoID = "35" +
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10;

        String serial = Build.getRadioVersion();
        String uuid = new UUID(uniquePseudoID.hashCode(), serial.hashCode()).toString();
        String brand = Build.BRAND;
        String modelno = Build.MODEL;
        String version = Build.VERSION.RELEASE;
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        ID.setString(StartMenuActivity.this, "uuid", "uuid", uuid);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dataReference = firebaseDatabase.getReference(uuid);
        dataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                   
                } else {
                    User user = new User(uuid, brand, modelno, version, android_id, 150);
                    db.child(uuid).setValue(user);

                    db.child(uuid).get().addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                         
                        } else {
                           
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
          
            }
        });

    }

```
## Model Class

```java
public class User {
    public String uuid;
    public String brand;
    public String modelno;
    public String version;
    public String android_id;
    public int coin;
    public User() {
    }
    public User(String uuid, String brand, String modelno, String version, String android_id, int coin) {
        this.uuid = uuid;
        this.brand = brand;
        this.modelno = modelno;
        this.version = version;
        this.android_id = android_id;
        this.coin = coin;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("coin", getCoin());
        return result;
    }
}
```
## Unrated Data History at Your Fingertips

Managing your data history is crucial, and Writey AI has it covered. We employ Room DB, a versatile solution that makes data deletion and organization a breeze. Store and retrieve data effortlessly, all arranged neatly for your convenience. Easily manage your content in a user-friendly interface.

## Entity

```java
@Entity
public class RoomUser {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "category")
    public String category;
    @ColumnInfo(name = "allData")
    public String allData;
    @ColumnInfo(name = "query")
    public String query;
    @ColumnInfo(name = "title")
    public String title;
}
```

## Dao

```java
@Dao
public interface UserDao {
    @Query("SELECT * FROM roomuser")
    List<RoomUser> getAllUsers();
    @Insert
    void insertUser(RoomUser... users);
    @Delete
    void delete(RoomUser user);
    @Query("DELETE FROM roomUser WHERE uid = :userId")
    abstract void deleteByUserId(long userId);
}
```

## Database

```java
@Database(entities = {RoomUser.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    private static AppDatabase INSTANCE;
    public static AppDatabase getDbInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DB_RESULT")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
```
## Stay Up-to-Date with Ease

We're committed to keeping Writey AI up-to-date for our users. Our app utilizes the latest technology to ensure a seamless experience. We employ Android Studio's flexible updates, making sure you always have access to the latest features and improvements. No need to worry about missing out on the latest advancements in social media content creation.

## InAppUpdate

```java
public class InAppUpdate {

    private final Activity parentActivity;

    private final AppUpdateManager appUpdateManager;

    private final int appUpdateType = AppUpdateType.FLEXIBLE;
    private final int MY_REQUEST_CODE = 500;

    public InAppUpdate(Activity activity) {
        this.parentActivity = activity;
        appUpdateManager = AppUpdateManagerFactory.create(parentActivity);
    }

    InstallStateUpdatedListener stateUpdatedListener = installState -> {
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            popupSnackBarForCompleteUpdate();
        }
    };

    public void checkForAppUpdate() {
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(info -> {
            boolean isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE;
            boolean isUpdateAllowed = info.isUpdateTypeAllowed(appUpdateType);

            if (isUpdateAvailable && isUpdateAllowed) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            info,
                            appUpdateType,
                            parentActivity,
                            MY_REQUEST_CODE
                    );
                } catch (IntentSender.SendIntentException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        appUpdateManager.registerListener(stateUpdatedListener);
    }

    public void onActivityResult(int requestCode, int resultCode) {
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode == RESULT_CANCELED) {
            } else if (resultCode != AppCompatActivity.RESULT_OK) {
                checkForAppUpdate();
            }
        }
    }

    private void popupSnackBarForCompleteUpdate() {
        Snackbar.make(
                parentActivity.findViewById(R.id.postsFrameLayout),
                "An update has just been downloaded.",
                Snackbar.LENGTH_INDEFINITE
        ).setAction(
                "RESTART", view -> {
                    if (appUpdateManager != null) {
                        appUpdateManager.completeUpdate();
                    }
                }
        ).show();
    }

    public void onResume() {
        if (appUpdateManager != null) {
            appUpdateManager.getAppUpdateInfo().addOnSuccessListener(info -> {
                if (info.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackBarForCompleteUpdate();
                }
            });
        }
    }

    public void onDestroy() {
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(stateUpdatedListener);
        }
    }

}
```

## Use

```java
 private InAppUpdate inAppUpdate;

 inAppUpdate = new InAppUpdate(MainActivity.this);
 inAppUpdate.checkForAppUpdate();

@Override
    protected void onResume() {
        super.onResume();
        inAppUpdate.onResume();
    }

@Override
    protected void onDestroy() {
        super.onDestroy();
        inAppUpdate.onDestroy();
    }
    
 @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inAppUpdate.onActivityResult(requestCode, resultCode);
    }
```
## Google AdMob

I use Google AdMob-rewarded ads to make money.

```java
public class AdMob {

    static OnDismiss onDismiss;
    public AdMob(OnDismiss onDismiss){
        this.onDismiss = onDismiss;
    }
    public AdMob(){
    }

    public static void loadAd(Context context){

        if (AdsUnit.isAds) {
            MobileAds.initialize(context, initializationStatus -> {

            });
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(context, AdsUnit.Reword,
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.d(TAG, loadAdError.toString());
                            AdsUnit.rewardedAd = null;
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd ad) {
                            AdsUnit.rewardedAd = ad;
                        }
                    });
        }
    }

    public void showAds(Activity activity, boolean isLode){

        if (AdsUnit.rewardedAd != null){
            AdsUnit.rewardedAd.show(activity, rewardItem -> {

            });

            AdsUnit.rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    if (isLode){
                        AdsUnit.rewardedAd = null;
                        AdMob.loadAd(activity);
                    }
                    onDismiss.onDismiss();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                }
            });
        }else {
            Toast.makeText(activity, "Please Press again..", Toast.LENGTH_LONG).show();
        }

    }
}
```
## Screenshots

![App Screenshot](/image/baner.png?raw=true)
![App Screenshot](/image/s1.png?raw=true)
![App Screenshot](/image/s2.png?raw=true)
![App Screenshot](/image/s3.png?raw=true)
![App Screenshot](/image/s4.png?raw=true)
![App Screenshot](/image/s5.png?raw=true)

