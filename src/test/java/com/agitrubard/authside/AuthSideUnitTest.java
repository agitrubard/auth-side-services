package com.agitrubard.authside;

import com.agitrubard.authside.auth.domain.token.AuthSideToken;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public abstract class AuthSideUnitTest {

    protected AuthSideToken mockAdminUserToken = AuthSideToken.builder()
            .accessToken("eyJ0eXAiOiJCZWFyZXIiLCJhbGciOiJSUzUxMiJ9.eyJqdGkiOiIyYjE5Mzc4Yy1mMmU3LTRhMzItYTNlMC0xOTdkYTNjY2IwMGQiLCJpc3MiOiJBVVRIX1NJREUiLCJpYXQiOjE3MDE3MDc2MTcsImV4cCI6MTcwMTcxNDgxNywidXNlclJvbGVzIjpbInJlYWQiLCJhZG1pbiJdLCJ1c2VyTGFzdExvZ2luRGF0ZSI6MTcwMTcxODQxNywidXNlckxhc3ROYW1lIjoiRGVtaXIiLCJ1c2VyUGVybWlzc2lvbnMiOlsidXNlcjpsaXN0Iiwicm9sZTp1cGRhdGUiLCJ1c2VyOmRlbGV0ZSIsInVzZXI6YWRkIiwicm9sZTpsaXN0Iiwicm9sZTpkZWxldGUiLCJ1c2VyOnVwZGF0ZSIsInVzZXI6ZGV0YWlsIiwicm9sZTpkZXRhaWwiLCJyb2xlOmFkZCJdLCJ1c2VyRmlyc3ROYW1lIjoiQWdpdCBSdWJhciIsInVzZXJJZCI6IjkzY2RkMjYwLTU3Y2QtNDUzNi1iZDE3LWUzNWI1YjdjNjhmZCIsInVzZXJuYW1lIjoiYWdpdHJ1YmFyZCJ9.MGt_v7PQFK4g9xBvWRMlINg1-M7gkYQUQDbiWYDW72pec2x0-PDWx22f6TAn9hnOm0pz-AiL-BHtdOxVFIbpm37EBrX0EiFyPdyv_WwkTzdzc5vVSH_oY9-ROEC2IDF9lhFRY4wMIfdLePHLIuC3Jpnwjy_nl2024G9-yeWJRMdBUAck8b02_HsCgIR4_wdVQNuhhxb8S4ughKrEnq3NCeWdIFGliMRZiJV82EM7tqUeBXUtzpOtrU9KdLNeGZAAJinOHXBZkXD5c1FJQBrqP1F6bu3bbN3o2rJrmNEqQzD9VZvBXzYXE8XkKlE7EqrFRVEQdrt02OYJQwWSMxsU_A")
            .accessTokenExpiresAt(1701714817L)
            .refreshToken("eyJ0eXAiOiJCZWFyZXIiLCJhbGciOiJSUzUxMiJ9.eyJqdGkiOiI3NzQ3ZjU5Zi1iZTgxLTQ5ZjctYWZiZC04MzA0Mzg4NDVlODgiLCJpc3MiOiJBVVRIX1NJREUiLCJpYXQiOjE3MDE3MDc2MTcsImV4cCI6MTcwMTc5NDAxNywidXNlcklkIjoiOTNjZGQyNjAtNTdjZC00NTM2LWJkMTctZTM1YjViN2M2OGZkIn0.gAdzJNbaiq5a2SSywBYsqmyfAh0oeXT7_8ZgS-Sjq0XH3yGTd_53kvPMrQs8FODgUEG768FPdY-D343MXJJL1ZLBLftK4Fn9yfubIC21VTQ_02-Ob5mS-LdnlM80sqfaYF4rIWtsbOuCX4BReq_qs5ypeCsjQykdVoqcMnjPjODej1WeSEi2iG3t9k3u9a7EPjWJEmiLfRLFfuAyhmlgeqKzxlEJLVzdiaeK7wvxjfmINo_xPd4teLTITh5_aaIFHgi66IK01xEEPw8XAPcs5B9m4c_whINZ9cbnH2XzX-NdM0PNqv9aWXRtwi9FQZCXHGNNX7kbWnGfSJaf-g1pRA")
            .build();

    protected AuthSideToken mockUserToken = AuthSideToken.builder()
            .accessToken("eyJ0eXAiOiJCZWFyZXIiLCJhbGciOiJSUzUxMiJ9.eyJqdGkiOiI2NTc1YzA1My02MDg5LTQ5MTEtYTg0My01YjU3MTc5ZWI5NjgiLCJpc3MiOiJBVVRIX1NJREUiLCJpYXQiOjE3MDE3MDc2MjgsImV4cCI6MTcwMTcxNDgyOCwidXNlclJvbGVzIjpbInJlYWQiXSwidXNlckxhc3RMb2dpbkRhdGUiOjE3MDE3MTg0MjgsInVzZXJMYXN0TmFtZSI6IkRlbWlyIiwidXNlclBlcm1pc3Npb25zIjpbInVzZXI6bGlzdCIsInJvbGU6bGlzdCIsInVzZXI6ZGV0YWlsIiwicm9sZTpkZXRhaWwiXSwidXNlckZpcnN0TmFtZSI6IkFnaXQgUnViYXIiLCJ1c2VySWQiOiJlODZmMDFlZS1iZGUyLTRhNGQtYjk4Yi1iN2E3NjA0MWMwYTciLCJ1c2VybmFtZSI6ImFnaXRydWJhcmRlbWlyIn0.mG1nFaIGKDF1dmalzJmJQ7bQzTYsCflU71RhFDiXCRBv16BqlXD6qtdrDD44YIW4NqBQzIxjTvB1OMkKo_oyC23ax1AQHUWpnrcbTNFMRLLOnhWBkVdjbe43vw72tbSrsRF5Lp1yUJnIqxCIMkn0gUy9JE99Jd4xasIf-xYRcDZy_BX4tg2HXcGhn310Mizv86oBODti4iWNMhMYBfwmpG--gY9d_qmEQ-6S5E4lE81YQhsznM7kVovNJpqlfKxlWG9l-C58nXvClrlCy0nyMDXIu1ZYiTIpoTo4y0Gfgn8QxtzHjk-_jWW9aXUt-glfKnmR1TFn8K2qOI1LNJNjCw")
            .accessTokenExpiresAt(1701714828L)
            .refreshToken("eyJ0eXAiOiJCZWFyZXIiLCJhbGciOiJSUzUxMiJ9.eyJqdGkiOiI5ODM2MDFlYy0yODFhLTQ0NGUtODQ4Yi1lNzI1MjE4MmUyYzciLCJpc3MiOiJBVVRIX1NJREUiLCJpYXQiOjE3MDE3MDc2MjgsImV4cCI6MTcwMTc5NDAyOCwidXNlcklkIjoiZTg2ZjAxZWUtYmRlMi00YTRkLWI5OGItYjdhNzYwNDFjMGE3In0.RxEO0N8KNSd10F0xX7SIb17B8LWsMeoilhKAAPPpGN1SMBENU-Z5eZ6xlqP1KcmKSgMQBof8DwtSYHow8RNPIxeulcRxQ6XQlO4ECxIbO2dM5J6uZ5tyncKhVJIJWr77ZEfVgd5f9uzQochPCFsvIiU7k6rnLfmnSgIDWG4OGoyEmeNS2Hk76DegnIOXh_DP5OB9lEwO36oY4Oz5wlLBSr5r2avvaHDGQ8guxwh5BmRGfmR0SC1Kd89zdA_1bHJQ_Z0pqnBkUyc1KkdR-S9cwLbVFVeTxX6dCnEDHVr4NxxLirg-7A_nEc-BC5USiKKuNB_IB0yGF28_GGph-CmO5A")
            .build();

}