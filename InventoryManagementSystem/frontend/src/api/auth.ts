import { apiFetch } from './client'
import type { AuthRequest, AuthResponse } from '../types'

export async function login(payload: AuthRequest): Promise<AuthResponse> {
  return apiFetch<AuthResponse>('/api/auth/login', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}
