package com.example.mtry

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.common.MapboxOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.atmosphere.generated.atmosphere
import com.mapbox.maps.extension.style.expressions.dsl.generated.get

import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.interpolate
import com.mapbox.maps.extension.style.layers.generated.SkyLayer
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.layers.generated.skyLayer
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.layers.properties.generated.SkyType
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.extension.style.terrain.generated.terrain
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.location

class MainActivity : AppCompatActivity(), PermissionsListener {

    private  lateinit var  mapView: MapView
    lateinit var permissionsManager: PermissionsManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        MapboxOptions.accessToken = "pk.eyJ1IjoibWtnYXRsZSIsImEiOiJjbHlpaWNtZXkwYzZzMmlzZWxzNjA0Y2l4In0.R3WiMQ6K-UjHAuzgtLj35g"

        setContentView(R.layout.activity_main)

        if (PermissionsManager.areLocationPermissionsGranted(this))
        {
            //do map things
            mapView = findViewById(R.id.mapView)



            mapView.mapboxMap.setCamera(

                CameraOptions.Builder()
                    .center(Point.fromLngLat(28.1879101, -25.7459277))
                    .pitch(0.0)
                    .zoom(15.0)
                    .bearing(180.0)
                    .build()
            )
            mapView.location.enabled = true

            mapView.mapboxMap.subscribeMapLoadingError {
                // Error occurred when loading the map, try to handle it gracefully here
            }

           //mapView.mapboxMap.loadStyle(Style.TRAFFIC_DAY)

            // Create an instance of the Annotation API and get the PointAnnotationManager.
            val annotationApi = mapView?.annotations
            val pointAnnotationManager = annotationApi?.createPointAnnotationManager()
// Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                // Define a geographic coordinate.
                .withPoint(Point.fromLngLat(29.1879101, -25.7459277))
                // Specify the bitmap you assigned to the point annotation
                // The bitmap will be added to map style automatically.
                .withIconImage(getDrawable(R.drawable.red_marker)!!.toBitmap())
// Add the resulting pointAnnotation to the map.
            pointAnnotationManager?.create(pointAnnotationOptions)


           /* mapView.mapboxMap.loadStyle(
                style(style = getString(R.string.mapbox_url)) {
                    +terrain("TERRAIN_SOURCE").exaggeration(1.5)
                    +projection(ProjectionName.GLOBE)
                    +atmosphere {  }
                    +skyLayer("sky"){
                        skyType(SkyType.ATMOSPHERE)
                        skyAtmosphereSun(listOf(-50.0,90.0))
                    }

                }
            ) { style ->
                // Map is set up and the style has loaded. Now you can add data or make other map adjustments.
            }

            */



        }
        else
        {
           permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(this)

        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onExplanationNeeded(permissionsToExplain: List<String>) {

    }

    override fun onPermissionResult(granted: Boolean) {

    }
}