<template>
  <div class="text-center mx-5">
    <div>BookShelf</div>
    <div class="bg-slate-400">
      <table class="bg-slate-600 w-full">
        <thead class="border-b-4 border-stone-900 text-white">
          <tr>
            <th class="py-3">#</th>
            <th>책</th>
            <th>상태</th>
            <th>시작일</th>
            <th>종료일</th>
            <th>기간</th>
          </tr>
        </thead>
        <tbody class="bg-slate-400">
          <tr v-for="book in testData" :key="book.id" class="border-b-2">
            <td class="py-3">{{ book.id }}</td>
            <td>{{ book.title }}</td>
            <td>{{ book.status }}</td>
            <td>{{ book.start }}</td>
            <td>{{ book.end }}</td>
            <td>{{ book.period }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
const testData = [
  {
    id: 1,
    title: '테스트 북 1',
    status: '완료',
    start: '2023.01.04',
    end: '2023.01.10',
    period: 6,
  },
  {
    id: 2,
    title: '테스트 북 2',
    status: '완료',
    start: '2023.01.04',
    end: '2023.01.10',
    period: 6,
  },
  {
    id: 3,
    title: '테스트 북 2',
    status: '완료',
    start: '2023.01.04',
    end: '2023.01.10',
    period: 6,
  },
];
</script>

<style lang="scss" scoped></style>
