package com.inekhaev.android.cryptoinvestor.domain.use_case.get_coin

import com.inekhaev.android.cryptoinvestor.common.Resource
import com.inekhaev.android.cryptoinvestor.data.remote.dto.toCoin
import com.inekhaev.android.cryptoinvestor.data.remote.dto.toCoinDetail
import com.inekhaev.android.cryptoinvestor.domain.model.Coin
import com.inekhaev.android.cryptoinvestor.domain.model.CoinDetail
import com.inekhaev.android.cryptoinvestor.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            emit(Resource.Success(coin))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}