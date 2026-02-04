import { apiFetch } from './client'
import type { Role, RoleCreatePayload } from '../types'

export async function listRoles(): Promise<Role[]> {
  return apiFetch<Role[]>('/api/roles')
}

export async function createRole(payload: RoleCreatePayload): Promise<Role> {
  return apiFetch<Role>('/api/roles', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}
