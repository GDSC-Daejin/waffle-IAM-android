package com.example.mysolutionchallenge.Navigation

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.example.mysolutionchallenge.LoginActivity
import com.example.mysolutionchallenge.MainActivity
import com.example.mysolutionchallenge.R
import com.example.mysolutionchallenge.databinding.FragmentAccountBinding
import com.example.mytodolist.SharedPref
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : PreferenceFragmentCompat() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var accountBinding: FragmentAccountBinding
    //상태유지
    var sharedPref : SharedPref? = null
    var pref : SharedPreferences? = null
    var darkthemePreference : SwitchPreferenceCompat? = null
    var smallthemePreference : SwitchPreferenceCompat? = null
    var middlethemePreference : SwitchPreferenceCompat? = null
    var largethemePreference : SwitchPreferenceCompat? = null
    var logoutPreference : Preference? = null
    var themeList : Preference? = null
    var isChecked = false
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        accountBinding = FragmentAccountBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }*/

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        sharedPref = this.context?.let { SharedPref(it) }
        if (sharedPref!!.loadNightModeState()) {
            context?.setTheme(R.style.darktheme)
        } else {
            context?.setTheme(R.style.AppTheme)
        }



        setPreferencesFromResource(R.xml.preference, rootKey)


        if (rootKey == null) {
            pref = activity?.let { PreferenceManager.getDefaultSharedPreferences(it)}
        }

        darkthemePreference = findPreference("themeKey0")
        smallthemePreference = findPreference("themeKey1")
        middlethemePreference = findPreference("themeKey2")
        largethemePreference = findPreference("themeKey3")
        /*if (sharedPref!!.loadNightModeState()) {
            themePreference!!.isChecked = true
        }*/
        var st = ""
        val selectTheme = findPreference<ListPreference>("themeList")!!
        selectTheme.setOnPreferenceChangeListener { preference, newValue ->
            val index = selectTheme.findIndexOfValue(newValue.toString())
            selectTheme.summary = selectTheme.entries[index]
            st = selectTheme.entryValues[index] as String

            when(st) {
                "DarkTheme" -> {
                    //true
                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey0", true).apply()
                    sharedPref!!.setNightModeState(true)

                    //false
                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey1", false).apply()
                    sharedPref!!.setSmallModeState(false)

                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey2", false).apply()
                    sharedPref!!.setMiddleModeState(false)

                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey3", false).apply()
                    sharedPref!!.setLargeModeState(false)
                    //
                    restartApp()
                }

                "SmallTheme" -> {
                    //true
                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey1", true).apply()
                    sharedPref!!.setSmallModeState(true)

                    //false
                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey0", false).apply()
                    sharedPref!!.setNightModeState(false)

                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey2", false).apply()
                    sharedPref!!.setMiddleModeState(false)

                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey3", false).apply()
                    sharedPref!!.setLargeModeState(false)
                    //
                    restartApp()
                }

                "AppTheme" -> {
                    //true
                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey2", true).apply()
                    sharedPref!!.setMiddleModeState(true)

                    //false
                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey0", false).apply()
                    sharedPref!!.setNightModeState(false)

                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey1", false).apply()
                    sharedPref!!.setSmallModeState(false)

                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey3", false).apply()
                    sharedPref!!.setLargeModeState(false)
                    //
                    restartApp()
                }

                "LargeTheme" -> {
                    //true
                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey3", true).apply()
                    sharedPref!!.setLargeModeState(true)

                    //false
                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey0", false).apply()
                    sharedPref!!.setNightModeState(false)

                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey2", false).apply()
                    sharedPref!!.setMiddleModeState(false)

                    preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey1", false).apply()
                    sharedPref!!.setSmallModeState(false)
                    //
                    restartApp()
                }
            }
            true
        }


        darkthemePreference!!.setOnPreferenceChangeListener { preference, newValue ->
            //var isChecked = false
            if (newValue as Boolean) {
                isChecked = newValue
            }
            if (isChecked) {
                preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey0", true).apply()
                sharedPref!!.setNightModeState(true)
                restartApp()
            } else {
                preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey0", false).apply()
                sharedPref!!.setNightModeState(false)
                restartApp()
            }
            return@setOnPreferenceChangeListener true
        }

        smallthemePreference!!.setOnPreferenceChangeListener { preference, newValue ->
            //var isChecked = false
            if (newValue as Boolean) {
                isChecked = newValue
            }
            if (isChecked) {
                preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey1", true).apply()
                sharedPref!!.setSmallModeState(true)
                restartApp()
            } else {
                preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey1", false).apply()
                sharedPref!!.setSmallModeState(false)
                restartApp()
            }
            return@setOnPreferenceChangeListener true
        }

        middlethemePreference!!.setOnPreferenceChangeListener { preference, newValue ->
            //var isChecked = false
            if (newValue as Boolean) {
                isChecked = newValue
            }
            if (isChecked) {
                preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey2", true).apply()
                sharedPref!!.setMiddleModeState(true)
                restartApp()
            } else {
                preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey2", false).apply()
                sharedPref!!.setMiddleModeState(false)
                restartApp()
            }
            return@setOnPreferenceChangeListener true
        }

        largethemePreference!!.setOnPreferenceChangeListener { preference, newValue ->
            //var isChecked = false
            if (newValue as Boolean) {
                isChecked = newValue
            }
            if (isChecked) {
                preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey3", true).apply()
                sharedPref!!.setLargeModeState(true)
                restartApp()
            } else {
                preferenceManager.sharedPreferences!!.edit().putBoolean("themeKey3", false).apply()
                sharedPref!!.setLargeModeState(false)
                restartApp()
            }
            return@setOnPreferenceChangeListener true
        }
        logoutPreference = preferenceManager.findPreference("logout")
        if (logoutPreference != null) {
            logoutPreference!!.setOnPreferenceClickListener {
                var auth: FirebaseAuth? = null
                //로그아웃
                auth = FirebaseAuth.getInstance()
                if (auth.currentUser != null) {
                    auth.signOut()

                    Toast.makeText(activity, "로그아웃되었습니다!", Toast.LENGTH_SHORT).show()

                    //로그아웃되었으니 로그인 화면으로 돌아감
                    val intent = Intent(activity, LoginActivity::class.java)
                    //상위 스택제거
                    //실행하는 액티비티가 스택에 있으면 새로 시작하지 않고 상위 스택 모두 제거.
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                } else {
                    Toast.makeText(activity, "로그인된 아이디가 없습니다!", Toast.LENGTH_SHORT).show()
                }

                return@setOnPreferenceClickListener true
            }
        }
        //themePreference!!.setOnPreferenceChangeListener(prefListener)
    }

    val prefListener =
        SharedPreferences
            .OnSharedPreferenceChangeListener { sharedPreferences: SharedPreferences?, key:String? ->
                when (key) {
                    /*"themeList" -> {
                        val selectTheme = findPreference<ListPreference>("themeList")!!
                        selectTheme.setOnPreferenceChangeListener { preference, newValue ->
                            val index = selectTheme.findIndexOfValue(newValue.toString())
                            selectTheme.summary = selectTheme.entries[index]

                            true
                        }
                    }*/

                }
            }

    //테마 변경 시 적용을 위한 재시작
    fun restartApp() {
        val intent = Intent(context?.applicationContext, MainActivity::class.java)
        activity?.startActivity(intent)
        activity?.finish()
    }

    // 리스너 등록
    /*override fun onResume() {
        super.onResume()
        pref!!.registerOnSharedPreferenceChangeListener(prefListener)
    }

    // 리스너 해제
    override fun onPause() {
        super.onPause()
        pref!!.unregisterOnSharedPreferenceChangeListener(prefListener)
    }*/

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountTestFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}