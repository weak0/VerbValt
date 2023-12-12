export function isEmpty(obj) {
    return Object.keys(obj).length === 0 && obj.constructor === Object;
}
export const learnTypeEnum = {
    Default: 0,
    Words: 1,
    Sentences: 2,
}

export const pageEnum = {
    Courses: 0,
    Words: 1,
    Learn: 2,
  }
  