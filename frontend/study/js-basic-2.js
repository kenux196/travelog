let user = {
  name: 'John',
  surname: 'Smith',
};
console.log(user);

user.name = 'Pete';
console.log(user);
delete user.name;
console.log(user);

let schedule = {};
console.log(isEmpty(schedule));

schedule['8:30'] = 'get up';
console.log(isEmpty(schedule));

function isEmpty(obj) {
  for (let key in obj) {
    return true;
  }
  return false;
}

let salaries = {
  John: 100,
  Ann: 160,
  Pete: 130,
};

function sum(obj) {
  let sum = 0;
  for (let key in obj) {
    sum += obj[key];
  }
  return sum;
}
console.log(sum(salaries));
