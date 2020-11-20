import * as Yup from 'yup'

const email = Yup.string()
    .email('Must enter a valid email')
    .required('email is required')

const phone = Yup.number()
    .required('Phone number is required')

const username = Yup.string()
    .min(4, 'Username must be 4+ characters')
    .max(50, 'Username must be less than 50 characters')
    .required('Username is required')

const password = Yup.string()
    .min(8, 'Password must be 8+ characters')
    .required('Password is required')

export {
    email,
    phone,
    password,
    username
}