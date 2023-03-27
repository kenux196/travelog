<template>
  <div class="text-center">
    <h1 class="font-bold text-2xl my-3">대구시 문화/공연/전시 행사 정보 검색</h1>
  </div>
  <div class="flex justify-end">
    <label for="selector"
      >행사종류
      <select id="selector" v-model="eventType" class="select bg-slate-800">
        <option v-for="item in eventTypeList" :key="item.id" :value="item.value">{{ item.name }}</option>
      </select>
    </label>
    <label
      >시작일
      <input type="date" v-model="startDate" :min="today" class="input bg-slate-800" />
    </label>
    <label
      >종료일
      <input type="date" v-model="endDate" class="input bg-slate-800" />
    </label>
    <button class="ml-3 btn" @click="searchEvent">검색</button>
  </div>
  <hr class="my-5" />
  <div class="overflow-x-auto">
    <div v-if="hasEvent">
      <table class="table table-compact w-full">
        <thead class="bg-slate-600 text-slate-100 text-center">
          <th>종류</th>
          <th>주제</th>
          <th>장소</th>
          <th>유료/무료</th>
          <th>기간</th>
        </thead>
        <tbody>
          <tr v-for="event in eventList" :key="event.id" class="border-b-2 border-slate-800">
            <td class="text-center">{{ event.event_gubun_name }}</td>
            <td class="text-left">{{ event.subject }}</td>
            <td class="text-center">{{ event.place }}</td>
            <td class="text-center">{{ event.pay_gubun_name }}</td>
            <td class="text-center">{{ event.start_date + ' ~ ' + event.end_date }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-else>검색 결과가 없습니다.</div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { computed, ref, inject } from 'vue';

const today = inject('today');
const startDate = ref(today);
const endDate = ref('');
const eventList = ref('');
const eventType = ref('');
const eventTypeList = ref([
  { id: 0, name: '선택하세요', value: '' },
  { id: 1, name: '전시', value: 'DP' },
  { id: 2, name: '공연', value: 'PF' },
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
      let id = 0;
      eventList.value.forEach((event) => {
        event.id = ++id;
      });
      console.log(eventList.value);
    })
    .catch((error) => {
      console.log(error);
    });
};
</script>
<style scoped>
input,
select {
  @apply border-solid border border-slate-800 py-3 m-2 px-8;
}
button {
  @apply bg-slate-800 text-white hover:bg-slate-700 rounded py-3 my-2 px-10;
}
</style>
