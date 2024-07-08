package com.example.kostroma;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;
import java.util.ArrayList;
import java.util.List;

public class Map extends AppCompatActivity {
    private MapView mapView;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private boolean isCentered = false;
    private PlacemarkMapObject playerPlacemark;
    private List<PointOfInterest> pointsOfInterest;
    private Point playerLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);

        locationManager = MapKitFactory.getInstance().createLocationManager();

        pointsOfInterest = new ArrayList<>();
        initializePoints();

        locationListener = new LocationListener() {
            @Override
            public void onLocationUpdated(@NonNull com.yandex.mapkit.location.Location location) {
                double latitude = location.getPosition().getLatitude();
                double longitude = location.getPosition().getLongitude();

                playerLocation = new Point(latitude, longitude);

                if (!isCentered) {
                    mapView.getMap().move(
                            new CameraPosition(new Point(latitude, longitude), 14.0f, 0.0f, 0.0f)
                    );
                    isCentered = true;
                }

                updatePlayerPlacemark(new Point(latitude, longitude));
            }

            @Override
            public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {
                // Обработка статуса местоположения (например, включен ли GPS)
            }
        };

        locationManager.subscribeForLocationUpdates(
                0.0,
                1000,
                1.0,
                false,
                com.yandex.mapkit.location.FilteringMode.OFF,
                locationListener
        );

        addPointsToMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
        addPointsToMap(); // Важно пересоздать точки интереса и их обработчики
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        locationManager.unsubscribe(locationListener);
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    private void initializePoints() {
        pointsOfInterest.add(new PointOfInterest(new Point(57.768686, 40.925823), "Пожарная каланча ", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.769329, 40.924729), "Полицейское управление", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity2.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.768801, 40.925978), "Памятник пожарным", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.769560, 40.926247), "Музей театрального костюма ", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.770198, 40.926041), "Дом В.Ф. Стожарова", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.768840, 40.926795), "Военно-исторический отдел Костромского музея-заповедника", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.771657, 40.929997), "Казённый винный склад, конец XIX-начало ХХ века", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.769598, 40.929805), "Романовский музей", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.769934, 40.930496), "Костромской государственный историко-архитектурный и художественный музей-заповедник", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.770402, 40.931305), "Театр имени А.Н. Островского", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.768225, 40.928556), "Особняк сенатора Борщова", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.768688, 40.934770), "Марьинский сквер", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.766905, 40.931601), "Дом городского головы Г.Н. Ботникова", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.766617, 40.933398), "Церковь Благовещения Пресвятой Богородицы", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.767365, 40.927778), "Статуя Снегурочки", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.766548, 40.929350), "Юрий Долгорукий", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
        pointsOfInterest.add(new PointOfInterest(new Point(57.766168, 40.929750), "Ювелир-кустарь", "Посмотреть информацию об этом месте?", R.drawable.my_location_icon, Activity1.class));
    }

    private void addPointsToMap() {
        mapView.getMap().getMapObjects().clear(); // Очищаем старые объекты перед добавлением новых
        for (PointOfInterest poi : pointsOfInterest) {
            mapView.getMap().getMapObjects().addPlacemark(
                    poi.getPoint(),
                    ImageProvider.fromResource(this, poi.getImageResource())
            ).addTapListener(new MapObjectTapListener() {
                @Override
                public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
                    if (playerLocation != null && isWithinDistance(point, playerLocation, 3000)) {
                        showPointDetail(poi);
                    } else {
                        Toast.makeText(Map.this, "Вы слишком далеко от этой точки", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });
        }
    }

    private void updatePlayerPlacemark(Point point) {
        if (playerPlacemark == null) {
            playerPlacemark = mapView.getMap().getMapObjects().addPlacemark(point, ImageProvider.fromResource(this, R.drawable.my_location_icon2));
        } else {
            playerPlacemark.setGeometry(point);
        }
    }

    private boolean isWithinDistance(Point point1, Point point2, double distance) {
        float[] results = new float[1];
        Location.distanceBetween(point1.getLatitude(), point1.getLongitude(), point2.getLatitude(), point2.getLongitude(), results);
        return results[0] < distance;
    }

    private void showPointDetail(PointOfInterest poi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(poi.getTitle());
        builder.setMessage(poi.getDescription());
        builder.setIcon(poi.getImageResource());

        builder.setPositiveButton("Закрыть", (dialog, which) -> {
            // Действие при нажатии OK
        });

        if (poi.getActivityClass() != null) {
            builder.setNeutralButton("Да", (dialog, which) -> {
                Intent intent = new Intent(this, poi.getActivityClass());
                startActivity(intent);
            });
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private static class PointOfInterest {
        private final Point point;
        private final String title;
        private final String description;
        private final int imageResource;
        private final Class<?> activityClass;

        public PointOfInterest(Point point, String title, String description, int imageResource, Class<?> activityClass) {
            this.point = point;
            this.title = title;
            this.description = description;
            this.imageResource = imageResource;
            this.activityClass = activityClass;
        }

        public Point getPoint() {
            return point;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public int getImageResource() {
            return imageResource;
        }

        public Class<?> getActivityClass() {
            return activityClass;
        }
    }
}
