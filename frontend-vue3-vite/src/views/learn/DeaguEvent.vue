<template>
  <h1>대구시 문화/공연/전시 행사 정보 검색</h1>
  <div class="grid">
    <div>
      <label>행사 종류</label>
      <select v-model="eventType">
        <option v-for="(item, index) in eventTypeList" :key="index" :value="item.value">{{ item.name }}</option>
      </select>
    </div>
    <div>
      <label>시작일</label>
      <input type="date" v-model="startDate" />
    </div>
    <div>
      <label>종료일</label>
      <input type="date" v-model="endDate" />
    </div>
    <div>
      <div>검색해봅시다.</div>
      <button @click="searchEvent">검색</button>
    </div>
  </div>
  <div>
    <div v-if="hasEvent">
      <h4>검색 결과</h4>
      <table>
        <thead>
          <th>행사종류</th>
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

const startDate = ref(new Date().toLocaleDateString());
const endDate = ref('');
const eventList = ref('');
const eventType = ref('');
const eventTypeList = ref([
  { name: '선택하세요', value: '' },
  { name: '전시', value: 'DP' },
  { name: '공연', value: 'PF' },
]);

const hasEvent = computed(() => {
  console.log('event list = ' + eventList.value.length);
  return eventList.value.length > 0 ? true : false;
});

const searchEvent = () => {
  if (eventType.value.length <= 0) {
    alert('행사 종류를 선택하세요.');
    return;
  }
  if (startDate.value.length <= 0) {
    alert('시작일을 입력하세요. 시작일은 필수입니다.');
    return;
  }

  console.log(import.meta.env.MODE);
  let requestUrl = '';
  if (import.meta.env.MODE !== 'development') {
    requestUrl = 'https://dgfca.or.kr';
  }

  axios
    .get(requestUrl + '/ajax/event/list.json', {
      params: {
        event_gubun: eventType.value,
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
