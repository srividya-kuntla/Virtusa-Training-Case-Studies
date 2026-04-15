!pip install pdfplumber
import nltk
import string
import pdfplumber
from nltk.corpus import stopwords
from collections import Counter

nltk.download('punkt')
nltk.download('stopwords')

# Read Resume (PDF or TXT)
def read_resume(file_path):
    text = ""

    if file_path.endswith(".pdf"):
        with pdfplumber.open(file_path) as pdf:
            for page in pdf.pages:
                text += page.extract_text() + " "
    else:
        with open(file_path, "r", encoding="utf-8") as file:
            text = file.read()

    return text.lower()

# Clean and tokenize text
def preprocess(text):
    tokens = nltk.word_tokenize(text)
    stop_words = set(stopwords.words('english'))

    cleaned = [
        word for word in tokens
        if word not in stop_words and word not in string.punctuation
    ]

    return cleaned

# Extract keywords
def extract_keywords(text):
    words = preprocess(text)
    frequency = Counter(words)
    return set(frequency.keys())

# Match Resume with Job Description
def match_resume(resume_text, job_text):
    resume_keywords = extract_keywords(resume_text)
    job_keywords = extract_keywords(job_text)

    matched = resume_keywords.intersection(job_keywords)

    score = (len(matched) / len(job_keywords)) * 100 if job_keywords else 0

    missing = job_keywords - resume_keywords

    return score, matched, missing

def main():
    print("Resume Analyzer & Job Matcher")

    resume_path = input("Enter resume file path (PDF/TXT): ")

    print("\nPaste Job Description (press Enter twice to finish):")
    job_lines = []
    while True:
        line = input()
        if line == "":
            break
        job_lines.append(line)

    job_description = " ".join(job_lines)

    resume_text = read_resume(resume_path)

    score, matched, missing = match_resume(resume_text, job_description)

    print("\n------ RESULT ------")
    print(f"Match Score: {score:.2f}%")

    print("\nMatched Skills:")
    print(", ".join(list(matched)[:15]))

    print("\nMissing Skills (Suggestions):")
    print(", ".join(list(missing)[:15]))


if __name__ == "__main__":
    main()