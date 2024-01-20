export interface Note {
  id: number;
  title: string;
  content: string;
  isPublic: boolean;
  isEncrypted: boolean;
  username: string;
}

export interface NoteDto {
  title: string;
  content: string;
  isPublic: boolean;
  isEncrypted: boolean;
  username: string;
}
