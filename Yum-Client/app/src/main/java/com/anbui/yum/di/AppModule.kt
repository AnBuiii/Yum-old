package com.anbui.yum.di

//import io.ktor.client.engine.android.*
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.anbui.yum.data.local.YumDatabase
import com.anbui.yum.data.local.recipe.RecipeEntity
import com.anbui.yum.data.remote.RecipeRemoteMediator
import com.anbui.yum.data.remote.recipe.RecipeServiceImpl
import com.anbui.yum.data.remote.implement.ReviewServiceImpl
import com.anbui.yum.data.remote.implement.UserInfoServiceImpl
import com.anbui.yum.data.remote.implement.UserServiceImpl
import com.anbui.yum.data.remote.recipe.RecipeService
import com.anbui.yum.data.remote.service.ReviewService
import com.anbui.yum.data.remote.service.UserInfoService
import com.anbui.yum.data.remote.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    },
                )
            }

        }
    }

    @Provides
    @Singleton
    fun provideYumDatabase(@ApplicationContext context: Context): YumDatabase {
        return Room.databaseBuilder(
            context,
            YumDatabase::class.java,
            "Yum.db",
        ).build()
    }


    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideUserService(client: HttpClient, prefs: SharedPreferences): UserService {
        return UserServiceImpl(prefs, client)
    }

    @Provides
    @Singleton
    fun provideUserInfoService(client: HttpClient, prefs: SharedPreferences): UserInfoService {
        return UserInfoServiceImpl(prefs, client)
    }

    @Provides
    @Singleton
    fun provideRecipeService(client: HttpClient): RecipeService {
        return RecipeServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideReviewService(client: HttpClient): ReviewService {
        return ReviewServiceImpl(client)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideRecipePager(yumDb: YumDatabase, recipeService: RecipeService): Pager<Int, RecipeEntity> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = RecipeRemoteMediator(
                yumDb = yumDb,
                recipeService = recipeService,
            ),
            pagingSourceFactory = {
                yumDb.recipeDao.pagingSource()
            },
        )
    }

//    @Provides
//    @Singleton
//    fun provideChatSocketService(client: HttpClient): ChatSocketService {
//        return ChatSocketServiceImpl(client)
//    }
}