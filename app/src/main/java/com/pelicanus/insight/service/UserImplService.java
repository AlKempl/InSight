package com.pelicanus.insight.service;

import android.util.Log;

import com.google.gson.Gson;
import com.pelicanus.insight.interfaces.UserService;
import com.pelicanus.insight.model.Resource;
import com.pelicanus.insight.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alkempl on 2/20/18.
 */

public class UserImplService {
    private static final String MAIN_URI = "https://ft-alexblack191198.oraclecloud2.dreamfactory.com/api/v2/db/_table/";

//    public User updateUser(User user) throws Exception {
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(MAIN_URI).addConverterFactory(GsonConverterFactory.create())
//                .build();
//        UserService service = retrofit.create(UserService.class);
//
//        return service.updateUser(user.getId(), user).execute().body();
//    }

//    public User createUser(User user) throws Exception {
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(MAIN_URI).addConverterFactory(GsonConverterFactory.create())
//                .build();
//        UserService bookResource = retrofit.create(UserService.class);
//        return bookResource.createUser(user).execute().body();
//
//    }

    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public String generateRandomEmail(String domain, int length) {
        return getRandomString(length) + "@" + domain;
    }

//    public User createRandomUser() throws Exception {
//        Random randomGenerator = new Random();
//
//        User usr = new User();
//        usr.setEmail(generateRandomEmail("gmail.com", 9));
//        usr.setId(UUID.randomUUID().toString());
//        ArrayList<String> names = new ArrayList<>(Arrays.asList("John Doe", "Sam Smith", "Joht Parsons", "Jay Smith"));
//        usr.setName(names.get(randomGenerator.nextInt(names.size())));
//        usr.setRating(3.14);
//        usr.setStatus("OK");
//
//        return createUser(usr);
//    }

    public void getAllUsers() throws Exception {
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(MAIN_URI).addConverterFactory(GsonConverterFactory.create())
//                .build();
//        UserService bookResource = retrofit.create(UserService.class);
//        Call<List<User>> books = bookResource.getAllUsers();
//        return books.execute().body();
        UserService userService = APIService.retrofit.create(UserService.class);
        final Call<Resource> call = userService.getAllUsers();

        call.enqueue(new Callback<Resource>() {
            @Override
            public void onResponse(Call<Resource> call, Response<Resource> response) {
//                             final TextView textView = (TextView) findViewById(R.id.textView);
//                             textView.setText(response.body().toString());
                Gson gson = new Gson();
                List<User> ulst = Arrays.asList(gson.fromJson(response.body().getResource(), User[].class));

                for (User x : ulst) {
                    Log.d("AHTUNG", "AHTUNG");
                    Log.d("DEBUG", x.toString());
                }
            }

            @Override
            public void onFailure(Call<Resource> call, Throwable t) {
//                             final TextView textView = (TextView) findViewById(R.id.textView);
//                             textView.setText("Something went wrong: " + t.getMessage());
                Log.d("AHTUNG2", t.getMessage() + " " + t.toString() + " " + t.getCause());
            }
        });
    }
//
//    public static void main(String[] args) throws Exception {
//        BookRepositoryImplRetrofit2 bookRepository = new BookRepositoryImplRetrofit2();
//        Book book = bookRepository.getAllBooks().get(0);
//        System.out.println(book);
//        // bookRepository.deleteBook(book.getId());
//    }
//
//    public void deleteBook(Long id) {
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(MAIN_URI).addConverterFactory(GsonConverterFactory.create())
//                .build();
//        BookResourceRetrofit2 bookResource = retrofit.create(BookResourceRetrofit2.class);
//        bookResource.deleteBook(id);
//    }
//
//    public Book findBookById(Long id) {
//        return null;
//    }

}
