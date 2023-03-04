<template>
  <h1>로그인 화면</h1>
  <div class="login">
    <input type="text" placeholder="email" v-model="user" />
    <input type="password" v-model="password" />
    <button type="submit" @click="login">로그인</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { auth } from '../api';

const user = ref('');
const password = ref('');

const login = () => {
  auth.login(user.value, password.value);
};
</script>

<style scoped>
.outer {
  width: 400px;
  margin: auto;
}
</style>
