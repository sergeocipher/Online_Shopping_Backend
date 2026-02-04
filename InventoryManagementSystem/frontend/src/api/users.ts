import { apiFetch } from './client'
import type { User, UserCreatePayload } from '../types'

export async function listUsers(): Promise<User[]> {
  return apiFetch<User[]>('/api/users')
}

export async function createUser(payload: UserCreatePayload): Promise<User> {
  return apiFetch<User>('/api/users', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}
