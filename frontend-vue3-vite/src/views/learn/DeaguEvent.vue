<template>
  <div class="text-center mx-10 text-slate-800">
    <h1 class="font-bold text-lg mb-3">대구시 문화/공연/전시 행사 정보 검색</h1>
    <hr class="my-3" />
    <div class="flex">
      <div class="">
        <label for="selector"
          >행사종류
          <select id="selector" v-model="eventType" class="border border-slate-800">
            <option v-for="item in eventTypeList" :key="item.id" :value="item.value">{{ item.name }}</option>
          </select>
        </label>
      </div>
      <div>
        <label
          >시작일
          <input type="date" v-model="startDate" />
        </label>
      </div>
      <div>
        <label
          >종료일
          <input type="date" v-model="endDate" />
        </label>
      </div>
      <div>
        <button class="ml-3" @click="searchEvent">검색</button>
      </div>
    </div>
  </div>
  <hr class="mx-10 my-5" />
  <div class="text-center mx-10">
    <div v-if="hasEvent">
      <table>
        <thead class="bg-slate-600 text-slate-100">
          <th class="w-10">종류</th>
          <th>주제</th>
          <th>장소</th>
          <th class="w-20">유료/무료</th>
          <th>기간</th>
        </thead>
        <tbody>
          <tr v-for="event in eventList" :key="event.id" class="border-b-2 border-slate-800">
            <td>{{ event.event_gubun_name }}</td>
            <td class="pl-3 text-left">{{ event.subject }}</td>
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
