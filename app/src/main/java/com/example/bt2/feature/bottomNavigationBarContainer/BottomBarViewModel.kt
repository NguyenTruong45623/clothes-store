package com.example.bt2.feature.bottomNavigationBarContainer

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.bt2.R
import com.example.bt2.feature.favouritePage.FavouritePageFragmentDirections
import com.example.bt2.feature.homePage.HomePageFragmentDirections
import com.example.bt2.repository.local.roomDataBase.AppDatabase
import com.example.bt2.repository.local.roomDataBase.FavouriteItem
import com.example.bt2.repository.online.ClotheData
import com.example.bt2.repository.online.RetrofitCallData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BottomBarViewModel(private val db: AppDatabase) : ViewModel(){
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _clothes = MutableStateFlow<List<ClotheData>>(emptyList())
    val clothes = _clothes.asStateFlow()

    private val _bottomBarUiState = MutableStateFlow(BottomBarUiState())
    val bottomBarUiState = _bottomBarUiState.asStateFlow()

    private val _clotheFavourite = MutableStateFlow<List<FavouriteClotheModel>>(emptyList())
    val clotheFavourite = _clotheFavourite.asStateFlow()

    private val _idFavourites = MutableStateFlow<List<FavouriteItem>>(emptyList())
    val idFavourites = _idFavourites.asStateFlow()

    private val _clothDetail = MutableStateFlow<FavouriteClotheModel?>(null)
    val clothDetail = _clothDetail.asStateFlow()


    init {
        fetch()
        viewModelScope.launch {
            db.favouriteItemDao().getAllFavouriteIds().collect {
                _idFavourites.value = it
                Log.d("BottomBarViewModel", "favourite: ${_idFavourites.value}")
            }

        }

        combine(_clothes, db.favouriteItemDao().getAllFavouriteIds()) { clothesList, favouriteIds ->
            clothesList.map { clothe ->
                FavouriteClotheModel(
                    id = clothe.id,
                    image = clothe.image,
                    name = clothe.name,
                    newest = clothe.newest,
                    popular = clothe.popular,
                    price = clothe.price,
                    rate = clothe.rate,
                    sex = clothe.sex,
                    isFavourite = favouriteIds.any { it.id == clothe.id }
                )
            }
        }.onEach { updatedList ->
            _clotheFavourite.value = updatedList
        }.launchIn(viewModelScope)


    }

    private fun fetch() {
        viewModelScope.launch {
            try {
                val responseCategory = RetrofitCallData.clothService.getCategory()
                val responseProduct = RetrofitCallData.clothService.getProduct()
                _categories.value = responseCategory.data.category
                _clothes.value = responseProduct.data.clothes
                val isLoading = _clothes.value.isEmpty() && _categories.value.isEmpty()
                _bottomBarUiState.value = _bottomBarUiState.value.copy(isLoading = isLoading)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onProductFavoriteClick(favouriteClotheModel: FavouriteClotheModel) {
        viewModelScope.launch {
            if(!favouriteClotheModel.isFavourite) {
                db.favouriteItemDao().insertFavourite(favouriteItem = FavouriteItem(favouriteClotheModel.id))
                _clotheFavourite.map { it ->
                    it.find { it.id == favouriteClotheModel.id }?.isFavourite = true
                }
                _clothDetail.update { it?.copy(isFavourite = true) } // cập nhập product detail
            }
            else {
                db.favouriteItemDao().deleteFavourite(favouriteItem = FavouriteItem(favouriteClotheModel.id))
                _clotheFavourite.map { it ->
                    it.find { it.id == favouriteClotheModel.id }?.isFavourite = false
                }
                _clothDetail.update { it?.copy(isFavourite = false) }

            }


        }
    }


    fun onClickProductDetail( view: View, favouriteClotheModel: FavouriteClotheModel) {
        val currentDestinationId = view.findNavController().currentDestination?.id
        viewModelScope.launch {
            _clothDetail.update { favouriteClotheModel }

            when(currentDestinationId) {
                R.id.homePage -> {
                    val action = HomePageFragmentDirections.actionHomePageToProductDetailFragment2()
                    view.findNavController().navigate(action)
                }
                R.id.favouritePage -> {
                    val action = FavouritePageFragmentDirections.actionFavouritePageToProductDetailFragment()
                    view.findNavController().navigate(action)
                }
            }

        }

    }


}