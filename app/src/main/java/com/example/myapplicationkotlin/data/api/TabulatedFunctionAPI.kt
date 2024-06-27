import com.example.myapplicationkotlin.domain.TabulatedFunctionDateNumbers
import retrofit2.Call
import retrofit2.http.GET

interface TabulatedFunctionAPI {
        @GET("/v3/d37e0991-00de-434c-a06e-6d0f200e5a86")
        fun getMyData(): Call<TabulatedFunctionDateNumbers>
}
