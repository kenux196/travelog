<template>
  <h3>회원가입</h3>
  <div>
    <input type="text" placeholder="이름을 입력하세요" v-model="name" />
    <input type="email" placeholder="이메일 입력하세요" v-model="email" />
    <input type="password" placeholder="비밀번호를 입력하세요" v-model="password" />
    <button type="submit" @click="signup">가입하기</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { auth } from '../../api';

const name = ref('');
const email = ref('');
const password = ref('');

const signup = () => {
  try {
    validate();
    auth.signup(name.value, email.value, password.value);
  } catch (e) {
    alert(e.message);
  }
};

const validate = () => {
  if (name.value === '') throw Error('이름을 입력하세요');
  if (email.value === '') throw Error('이메일 입력하세요');
  if (password.value === '') throw Error('패스워드를 입력하세요');
};
</script>

<style scoped>
.outer {
  width: 400px;
  margin: auto;
}
</style>
