package com.redkapetpty.shop2shopassesment.data.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.math.BigDecimal

class Converters {
	@TypeConverter fun bigDecimalFromString(v: String?) : BigDecimal? = v?.let {BigDecimal(it)}
	@TypeConverter fun stringFromBigDecimal(b: BigDecimal?) : String? = b?.toPlainString()
}
