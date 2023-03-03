import axios from 'axios';
import { useAuthStore } from './../stores/auth';

const authStore = useAuthStore();

export async function login(username, password) {
  axios
    .post('/api/auth/login', {
      username: username,
      password: password,
    })
    .then((response) => {
      if (response.status === 200) {
        console.log(response.data);
        console.log('accessToken: ' + response.data.accessToken);
        authStore.accessToken = response.data.accessToken;
        authStore.refreshToken = response.data.refreshToken;
        authStore.role = response.data.role;
        return true;
      }
    })
    .catch((e) => {
      console.error('error : ', e);
    });
  return false;
}
