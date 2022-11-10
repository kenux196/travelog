<template>
    <div class="protected" v-if="loginSuccess">
        <h1>
            <b-bage variant="success">보안 사이트에 대한 액세스가 허용되었습니다.</b-bage>
        </h1>
        <h5>로그인 성공!</h5>
    </div>
    <div class="unprotected" v-else-if="loginError">
        <h1>
            <b-badge variant="danger">이 페이지에 대한 접근 권한이 없습니다.</b-badge>
        </h1>
        <h5>로그인 실패!</h5>
    </div>
    <div class="unprotected" v-else>
        <h1>
            <b-badge variant="info">로그인 해 주세요.</b-badge>
        </h1>
        <h5>로그인 하지 않았습니다. 로그인을 해 주세요.</h5>
    </div>
    <form @submit.prevent="login()">
        <label>
            <input type="text" placeholder="username" v-model="user" />
        </label>
        <label>
            <input type="password" placeholder="password" v-model="password" />
        </label>
        <b-btn variant="success" type="submit">Login</b-btn>
        <p v-if="error" class="error">Bad login information.</p>
    </form>
</template>

<script>
import axios from "axios";

export default {
    name: "LoginItem",
    data() {
        return {
            loginSuccess: false,
            loginError: false,
            user: "",
            password: "",
            error: false,
        };
    },
    methods: {
        async login() {
            try {
                const result = await axios.get("/api/login", {
                    auth: {
                        username: this.user,
                        password: this.password,
                    },
                });
                if (result.status === 200) {
                    this.loginSuccess = true;
                }
            } catch (err) {
                this.loginError = true;
                throw new Error(err);
            }
        },
    },
};
</script>

<style scoped></style>
