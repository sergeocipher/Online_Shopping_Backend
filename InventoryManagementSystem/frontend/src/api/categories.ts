import { apiFetch } from './client'
import type { Category, CategoryCreatePayload } from '../types'

export async function listCategories(): Promise<Category[]> {
  return apiFetch<Category[]>('/api/categories')
}

export async function createCategory(payload: CategoryCreatePayload): Promise<Category> {
  return apiFetch<Category>('/api/categories', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}
