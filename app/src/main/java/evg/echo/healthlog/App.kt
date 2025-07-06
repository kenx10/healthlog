package evg.echo.healthlog

import android.app.Application
import androidx.room.Room
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import evg.echo.healthlog.model.AppDB
import evg.echo.healthlog.services.LocationService
import evg.echo.healthlog.services.MeasureService
import evg.echo.healthlog.services.UserService
import evg.echo.healthlog.vm.AnalyticsViewModel
import evg.echo.healthlog.vm.HistoryViewModel
import evg.echo.healthlog.vm.PersonalizationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import org.koin.dsl.onClose

val appModule = module {
    single {
        Room
            .databaseBuilder(androidContext(), AppDB::class.java, "app1.db")
            .build()
    }

    single {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }

    // dao
    single { get<AppDB>().userInfoDao() }
    single { get<AppDB>().migraineDao() }
    single { get<AppDB>().sugarDao() }
    single { get<AppDB>().pressureDao() }
    single { get<AppDB>().panicDao() }

    // serv
    single { UserService(get()) }
    single { LocationService(get(), get()) }
    single { MeasureService(get(), get(), get(), get(), get(), get()) }.onClose { it?.cleanup() }

    // vm
    viewModel { HistoryViewModel(get()) }
    viewModel { PersonalizationViewModel(get()) }
    viewModel { AnalyticsViewModel(get()) }
}


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }

        AndroidThreeTen.init(this)
    }
}