package engine.pp.ww_p_mobile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private ImageView imageView;
    private Button prevButton;
    private Button nextButton;
    private List<String> imageIds; // Use String for image URLs
    private int currentImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Glide.get(this).setLogLevel(Log.VERBOSE); // Włącz verbose

        categorySpinner = findViewById(R.id.categorySpinner);
        imageView = findViewById(R.id.imageView);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);

        imageIds = new ArrayList<>();

        // Create an ArrayAdapter using a string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.categories_array,
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                loadImagesByCategory(selectedCategory);
                showImage();
                Toast.makeText(MainActivity.this, "Selected category: " + selectedCategory, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing if no item is selected
            }
        });


        prevButton.setOnClickListener(v -> showPreviousImage());
        nextButton.setOnClickListener(v -> showNextImage());

        // Initial category loading (optional, can choose to load a default if needed)
        String defaultCategory = categorySpinner.getSelectedItem().toString();
        loadImagesByCategory(defaultCategory);
        showImage();
    }

    // Aktualizacja wyświetlanego obrazu
    private void updateImage() {
        if (!imageIds.isEmpty() && currentImageIndex >= 0 && currentImageIndex < imageIds.size()) {
            Glide.with(this)
                    .load(imageIds.get(currentImageIndex))
                    //.error(R.drawable.placeholder_image) // Dodaj placeholder obraz
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e("Glide", "Error loading image", e); // Logowanie błędu
                            return false; // ważne aby zwrócić false, aby Glide spróbował wyświetlić placeholder
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageView);
        } else {
            imageView.setImageDrawable(null); // Clear ImageView if no image
        }
    }

    // Metoda wyświetlająca poprzedni obraz
    private void showPreviousImage() {
        currentImageIndex--;
        if (currentImageIndex < 0) {
            currentImageIndex = imageIds.size() - 1;
        }
        updateImage();
    }

    // Metoda wyświetlająca następny obraz
    private void showNextImage() {
        currentImageIndex++;
        if (currentImageIndex >= imageIds.size()) {
            currentImageIndex = 0;
        }
        updateImage();
    }


    private void showImage() {
        if (!imageIds.isEmpty() && currentImageIndex >= 0 && currentImageIndex < imageIds.size()) {
            Glide.with(this)
                    .load(imageIds.get(currentImageIndex))
                    //.error(R.drawable.placeholder_image) // Ustaw domyślny obraz
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e("Glide", "Error loading image", e); // logowanie błędu
                            return false; // ważne aby zwrócić false, aby Glide spróbował wyświetlić placeholder
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageView);
        } else {
            imageView.setImageDrawable(null); // Clear ImageView if no image
        }
    }

    private void loadImagesByCategory(String category) {
        imageIds.clear();
        String baseUrl = "https://raw.githubusercontent.com/PiotrIT2015/WW-P-mobile/main/img/";
        switch (category) {
            case "ikigai":
                String url0 = baseUrl + "ikigai.jpeg";
                imageIds.add(url0);
                break;
            case "mission":
                for(int i = 1; i <= 2; i++) {
                    String url = baseUrl + "mission" + i + ".jpg";
                    imageIds.add(url);
                    Log.d("URL_DEBUG", "mission URL: " + url); // Logowanie URL-a
                }
                break;
            case "passion":
                for(int i = 1; i <= 2; i++) {
                    String url = baseUrl + "passion" + i + ".jpg";
                    imageIds.add(url);
                    Log.d("URL_DEBUG", "passion URL: " + url);
                }
                break;
            case "vacation":
                for(int i = 1; i <= 2; i++) {
                    String url = baseUrl + "vacation" + i + ".jpg";
                    imageIds.add(url);
                    Log.d("URL_DEBUG", "vacation URL: " + url);
                }
                break;
            case "profession":
                for(int i = 1; i <= 2; i++) {
                    String url = baseUrl + "profession" + i + ".jpg";
                    imageIds.add(url);
                    Log.d("URL_DEBUG", "profession URL: " + url);
                }
                break;
            default:
                return;
        }
        currentImageIndex = 0;
    }
}