<template>
  <h1>Learn JS</h1>

  <ul>
    <li>Promise</li>
    <li>Async & Await</li>
  </ul>
</template>

<script setup>
// use promise
function fetchUser() {
  return new Promise((resolve, reject) => {
    // do something
    resolve('user1');
  });
}

const user = fetchUser();
console.log(user);
user.then(console.log);

// use aync
async function fetchMember() {
  return 'member1';
}

const member = fetchMember();
console.log(member);
member.then(console.log);

// await
function delay(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

async function getApple() {
  await delay(2000);
  return '🍎';
}

async function getBanana() {
  await delay(1000);
  return '🍌';
}

async function pickFruits() {
  // 1. chaining
  // return getApple().then((apple) => {
  //   return getBanana().then((banana) => `${apple} + ${banana}`);
  // });
  // 2. await 일반적인 사용
  // const apple = await getApple();
  // const banana = await getBanana();
  // 3. 병렬 처리
  const applePromise = getApple();
  const bananaPromise = getBanana();
  const apple = await applePromise;
  const banana = await bananaPromise;
  return `${apple} + ${banana}`;
}
pickFruits().then(console.log);

// 4 useful APIs
function pickAllFruits() {
  return Promise.all([getApple(), getBanana()]).then((fruits) => fruits.join(' + '));
}

pickAllFruits().then(console.log);

function pickOnlyOne() {
  return Promise.race([getApple(), getBanana()]);
}

pickOnlyOne().then(console.log);
</script>
