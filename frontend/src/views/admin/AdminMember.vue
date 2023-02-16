<template>
  <h1>Admin 회원 관리 페이지</h1>
  <table>
    <thead>
      <th>제목</th>
      <th>저자</th>
      <th>등록일</th>
    </thead>
    <tbody>
      <tr v-for="member in members" :key="member.id">
        <td>{{ member.name }}</td>
        <td>{{ member.role }}</td>
        <td>{{ member.createDate }}</td>
      </tr>
    </tbody>
  </table>
</template>
<script>
export default {
  name: 'VueTestView',
  data() {
    return {
      message: '위험',
      members: [
        {
          id: 1,
          name: 'kenux1',
          role: 'admin',
          createDate: '2020-02-04',
        },
        {
          id: 2,
          name: 'kenux2',
          role: 'user',
          createDate: '2020-02-04',
        },
        {
          id: 1,
          name: 'kenux3',
          role: 'user',
          createDate: '2020-02-04',
        },
      ],
    };
  },
};
// todo - 회원 리스트 조회 추가
</script>
