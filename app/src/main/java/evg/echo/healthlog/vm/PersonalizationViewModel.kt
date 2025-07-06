package evg.echo.healthlog.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import evg.echo.healthlog.model.ent.Gender
import evg.echo.healthlog.services.UserService
import kotlinx.coroutines.launch
import java.util.Calendar

class PersonalizationViewModel(
    val userService: UserService
) : ViewModel() {
    var name: MutableState<String> = mutableStateOf("")
    val email: MutableState<String?> = mutableStateOf(null)
    val gender: MutableState<Gender> = mutableStateOf(Gender.MALE)
    val dateInMillis: MutableState<Long> = mutableLongStateOf(mills18YearsBefore())

    init {
        viewModelScope.launch {
            name.value = userService.getFIO() ?: ""
            email.value = userService.getEmail()
            gender.value = userService.getGender() ?: Gender.MALE
            dateInMillis.value = userService.getBirth() ?: mills18YearsBefore()
        }
    }

    fun save(name: String, email: String?, gender: Gender, dateInMillis: Long) {
        viewModelScope.launch {
            userService.setFIO(name)
            if (null != email)
                userService.setEmail(email)
            userService.setGender(gender)
            userService.setBirth(dateInMillis)
        }
    }

    fun mills18YearsBefore(): Long {
        return Calendar.getInstance()
            .apply { add(Calendar.YEAR, -18) }
            .timeInMillis
    }
}