<template>
  <h1>대구시 문화/공연/전시 이벤트 정보 검색</h1>
  <div class="grid">
    <input type="date" v-model="startDate" />
    <input type="date" v-model="endDate" />
    <button @click="searchEvent">검색</button>
  </div>
  <div>
    <div v-if="hasEvent">
      <h4>검색 결과</h4>
      <p>검색 결과가 있습니다.</p>
      <table>
        <thead>
          <th>종류</th>
          <th>주제</th>
          <th>장소</th>
          <th>유료/무료</th>
          <th>기간</th>
        </thead>
        <tbody>
          <tr v-for="(event, index) in eventList" :key="index">
            <td>{{ event.event_gubun_name }}</td>
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

const startDate = ref('');
const endDate = ref('');
const eventList = ref('');

const hasEvent = computed(() => {
  console.log('event list = ' + eventList.value.length);
  return eventList.value.length > 0 ? true : false;
});

const searchEvent = () => {
  if (startDate.value.length <= 0) {
    alert('시작일을 입력하세요. 시작일은 필수입니다.');
    return;
  }

  axios
    .get('/ajax/event/list.json', {
      params: {
        event_gubun: 'DP',
        start_date: startDate.value.toString(),
        end_date: endDate.value.toString(),
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
