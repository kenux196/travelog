<template>
  <h1>책 검색</h1>
  <input type="text" v-model="keyword" />
  <button @click="searchEvent">검색</button>
  <div>
    <div v-if="hasEvent">
      <h4>검색 결과</h4>
      <p>검색 결과가 있습니다.</p>
      <table>
        <thead>
          <th>종류</th>
          <th>제목</th>
          <th>장소</th>
          <th>가격</th>
          <th>기간</th>
        </thead>
        <tbody>
          <tr v-for="(event, index) in eventList" :key="index">
            <td>
              {{ event.event_gubun_name }}
            </td>
            <td>{{ event.subject }}</td>
            <td>{{ event.place }}</td>
            <td>{{ event.pay_gubun_name }}</td>
            <td>{{ event.start_date + ' ~ ' + event.end_date }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-else>검색 결과가 없습니다.</div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { computed, ref } from 'vue';

const keyword = ref('');
const eventList = ref('');

const hasEvent = computed(() => {
  console.log('book list = ' + eventList.value.length);
  return eventList.value.length > 0 ? true : false;
});

const searchEvent = () => {
  if (keyword.value.length <= 0) {
    alert('검색할 책 정보를 입력하세요.');
    return;
  }
  console.log(keyword.value + ' 책을 검색합니다.');

  axios
    .get('https://dgfca.or.kr/ajax/event/list.json', {
      params: {
        event_gubun: 'DP',
        start_date: '2023-03',
        end_date: '2023-04',
      },
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE',
        'Access-Control-Allow-Headers': 'Origin, Content-Type, X-Auth-Token',
      },
    })
    .then((response) => {
      console.log(response);
      eventList.value = response.data;
      console.log(eventList.value);
    })
    .catch((error) => {
      console.log(error);
    });
};
</script>
